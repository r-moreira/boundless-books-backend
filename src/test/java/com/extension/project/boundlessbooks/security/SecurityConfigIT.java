package com.extension.project.boundlessbooks.security;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import com.extension.project.boundlessbooks.model.entity.UserProfile;
import com.extension.project.boundlessbooks.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = "application.flags.enable-api-key-validation=false")
@AutoConfigureMockMvc
class SecurityConfigIT {
    @Autowired
    ApplicationProperties applicationProperties;

    @MockitoBean
    private UserProfileRepository userProfileRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpoints_ShouldBeAccessibleWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/users/search"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get(applicationProperties.getOauth2().getLoginPath()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void authenticatedEndpoints_ShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticatedEndpoints_ShouldReturnUserProfile() throws Exception {
        UserProfile mockedUser = new UserProfile();
        mockedUser.setId(123L);

        Mockito.when(userProfileRepository.findByGoogleUserId(String.valueOf(mockedUser.getId())))
                .thenReturn(Optional.of(mockedUser));

        Map<String, Object> claims = Map.of(
                StandardClaimNames.SUB, "123",
                "iss", "https://accounts.google.com"
        );
        OidcIdToken idToken = new OidcIdToken("tokenValue", null, null, claims);
        DefaultOidcUser oidcUser = new DefaultOidcUser(null, idToken);

        mockMvc.perform(get("/api/v1/users/me")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(
                                new TestingAuthenticationToken(oidcUser, null, "ROLE_USER")
                        )))
                .andExpect(status().isOk());
    }

    @Test
    void logout_ShouldInvalidateSessionAndRedirect() throws Exception {
        mockMvc.perform(get(applicationProperties.getOauth2().getLogoutPath()))
                .andExpect(status().is3xxRedirection());
    }
}