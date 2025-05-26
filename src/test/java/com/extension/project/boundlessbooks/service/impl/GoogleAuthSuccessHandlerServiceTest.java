package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import com.extension.project.boundlessbooks.model.entity.UserProfile;
import com.extension.project.boundlessbooks.repository.GoogleUserRepository;
import com.extension.project.boundlessbooks.repository.UserProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GoogleAuthSuccessHandlerServiceTest {

    @Mock
    private GoogleUserRepository googleUserRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ApplicationProperties.OAuth2 oauth2Properties;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private GoogleAuthSuccessHandlerService googleAuthSuccessHandlerService;

    private final DefaultOAuth2User userDetails = new DefaultOAuth2User(null, Map.of("sub", "12345", "email", "test@example.com"), "sub");

    @BeforeEach
    void setUp() {
        when(applicationProperties.getOauth2()).thenReturn(oauth2Properties);
        when(applicationProperties.getOauth2().getRedirectUri()).thenReturn("http://redirect-uri");
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    void onAuthenticationSuccess_NewUser_CreatesUserProfile() throws IOException {
        when(googleUserRepository.existsById("12345")).thenReturn(false);

        googleAuthSuccessHandlerService.onAuthenticationSuccess(request, response, authentication);

        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
        verify(response, times(1)).sendRedirect("http://redirect-uri");
    }

    @Test
    void onAuthenticationSuccess_ExistingUser_DoesNotCreateUserProfile() throws IOException {
         when(googleUserRepository.existsById("12345")).thenReturn(true);

        googleAuthSuccessHandlerService.onAuthenticationSuccess(request, response, authentication);

        verify(userProfileRepository, never()).save(any(UserProfile.class));
        verify(response, times(1)).sendRedirect("http://redirect-uri");
    }

    @Test
    void onAuthenticationSuccess_InvalidUser_DoesNothing() throws IOException {
        when(authentication.getPrincipal()).thenReturn(new Object());

        googleAuthSuccessHandlerService.onAuthenticationSuccess(request, response, authentication);

        verify(userProfileRepository, never()).save(any(UserProfile.class));
        verify(response, never()).sendRedirect("http://redirect-uri");
    }
}