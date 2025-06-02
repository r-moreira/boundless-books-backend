package com.extension.project.boundlessbooks.aspect;

import com.extension.project.boundlessbooks.exception.UnauthorizedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class OidcUserValidationAspectTest {

    @InjectMocks
    private OidcUserValidationAspect aspect;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private OidcUser oidcUser;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void validateOidcUser_ShouldPass_WhenOidcUserIsValid() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(oidcUser);

        aspect.validateOidcUser();
    }

    @Test
    void validateOidcUser_ShouldThrowException_WhenPrincipalIsNotOidcUser() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new Object());

        assertThrows(UnauthorizedException.class, aspect::validateOidcUser);
    }

    @Test
    void validateOidcUser_ShouldThrowException_WhenPrincipalIsNull() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(null);

        assertThrows(UnauthorizedException.class, aspect::validateOidcUser);
    }
}