package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.mapper.GoogleUserMapper;
import com.extension.project.boundlessbooks.model.entity.GoogleUser;
import com.extension.project.boundlessbooks.model.entity.UserProfile;
import com.extension.project.boundlessbooks.repository.GoogleUserRepository;
import com.extension.project.boundlessbooks.repository.UserProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleAuthSuccessHandlerService implements AuthenticationSuccessHandler {
    private final GoogleUserRepository googleUserRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User userDetails) {

            GoogleUser googleUser = GoogleUserMapper.INSTANCE.fromOAuth2User(userDetails);

            if (!googleUserRepository.existsById(googleUser.getId())) {
                UserProfile userProfile = new UserProfile();
                userProfile.setGoogleUser(googleUser);
                userProfileRepository.save(userProfile);
            }

            response.sendRedirect("/api/v1/users/me");
        }
    }

}
