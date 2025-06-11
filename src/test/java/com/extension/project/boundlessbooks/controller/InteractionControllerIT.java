package com.extension.project.boundlessbooks.controller;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"h2", "kafka"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class InteractionControllerIT {

    public static final String X_API_KEY = "X-API-KEY";

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        OidcIdToken idToken = new OidcIdToken(
                "id-token",
                Instant.now(),
                Instant.now().plusSeconds(3600),
                Map.of("sub", "john.doe", "iss", "https://accounts.google.com")
        );

        OidcUserInfo userInfo = new OidcUserInfo(Map.of("name", "John Doe", "email", "user@example.com"));

        OidcUser oidcUser = new DefaultOidcUser(null, idToken, userInfo);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(oidcUser, null, oidcUser.getAuthorities())
        );
    }

    @Order(0)
    @Test
    void processInteraction_AddFavorite_ReturnsSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/interactions/favorite_add/1")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.processId").isNotEmpty());
    }

    @Order(1)
    @Test
    void processInteraction_RemoveFavorite_ReturnsSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/interactions/favorite_remove/1")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.processId").isNotEmpty());
    }

    @Order(2)
    @Test
    void processInteraction_AddToShelf_ReturnsSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/interactions/shelf_add/1")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.processId").isNotEmpty());
    }

    @Order(3)
    @Test
    void processInteraction_RemoveFromShelf_ReturnsSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/interactions/shelf_remove/1")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.processId").isNotEmpty());
    }

    @Order(4)
    @Test
    void processInteraction_ReadStart_ReturnsSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/interactions/read_start/1")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.processId").isNotEmpty());
    }

    @Order(5)
    @Test
    void processInteraction_ReadEnd_ReturnsSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/interactions/read_end/1")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.processId").isNotEmpty());
    }

    @Order(6)
    @Test
    void createInteraction_InvalidType_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/interactions/invalid/1")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}