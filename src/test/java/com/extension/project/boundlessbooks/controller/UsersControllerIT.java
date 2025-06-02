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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class UsersControllerIT {

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
    void getAllUsers_ReturnsUserProfiles_doNotIncludeBooks() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("include-books", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.id").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.email").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.name").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.createdAt").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$[*].shelfBooks").doesNotExist())
                .andExpect(jsonPath("$[*].shelfBooks").doesNotExist());

    }

    @Order(1)
    @Test
    void getAllUsers_ReturnsUserProfiles_doNotIncludeBooks_FilterByName() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("include-books", "false")
                        .param("name", "John Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.id").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.email").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.name").value("John Doe"))
                .andExpect(jsonPath("$[*].googleUser.createdAt").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$[*].updatedAt").isNotEmpty())
                .andExpect(jsonPath("$[*].shelfBooks").doesNotExist())
                .andExpect(jsonPath("$[*].shelfBooks").doesNotExist());
    }

    @Order(2)
    @Test
    void getAllUsers_ReturnsUserProfiles_IncludeBooks() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("include-books", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.id").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.email").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.name").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.createdAt").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$[*].updatedAt").isNotEmpty())
                .andExpect(jsonPath("$[*].favoriteBooks").isArray())
                .andExpect(jsonPath("$[*].shelfBooks").isArray());
    }


    @Order(3)
    @Test
    void getAllUsers_ReturnsUserProfiles_IncludeBooks_FilterByName() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("include-books", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.id").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.email").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.name").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.createdAt").isNotEmpty())
                .andExpect(jsonPath("$[*].googleUser.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$[*].updatedAt").isNotEmpty())
                .andExpect(jsonPath("$[*].favoriteBooks").isArray())
                .andExpect(jsonPath("$[*].shelfBooks").isArray());
    }

    @Order(4)
    @Test
    void getAllUsersPaginated_ReturnsUserProfiles_doNotIncludeBooks() throws Exception {
        mockMvc.perform(get("/api/v1/users/search/paginated")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("include-books", "false")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(5))
                .andExpect(jsonPath("$.content[*].id").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.id").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.email").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.name").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].updatedAt").isNotEmpty());
    }

    @Order(5)
    @Test
    void getAllUsersPaginated_ReturnsUserProfiles_doNotIncludeBooks_FilterByName() throws Exception {
        mockMvc.perform(get("/api/v1/users/search/paginated")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("include-books", "false")
                        .param("name", "John Doe")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(1))
                .andExpect(jsonPath("$.content[*].id").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.id").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.email").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.name").value("John Doe"))
                .andExpect(jsonPath("$.content[*].googleUser.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].updatedAt").isNotEmpty());
    }

    @Order(6)
    @Test
    void getAllUsersPaginated_ReturnsUserProfiles_IncludeBooks() throws Exception {
        mockMvc.perform(get("/api/v1/users/search/paginated")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("include-books", "true")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(5))
                .andExpect(jsonPath("$.content[*].id").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.id").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.email").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.name").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].favoriteBooks").isArray())
                .andExpect(jsonPath("$.content[*].shelfBooks").isArray());
    }

    @Order(7)
    @Test
    void getAllUsersPaginated_ReturnsUserProfiles_IncludeBooks_FilterByName() throws Exception {
        mockMvc.perform(get("/api/v1/users/search/paginated")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("include-books", "true")
                        .param("name", "John Doe")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(1))
                .andExpect(jsonPath("$.content[*].id").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.id").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.email").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.name").value("John Doe"))
                .andExpect(jsonPath("$.content[*].googleUser.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].googleUser.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].favoriteBooks").isArray())
                .andExpect(jsonPath("$.content[*].shelfBooks").isArray());
    }


    @Order(8)
    @Test
    void getCurrentUser_ReturnsUserProfile_doNotIncludeBooks() throws Exception {
        mockMvc.perform(get("/api/v1/users/me")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.googleUser.id").value("john.doe"))
                .andExpect(jsonPath("$.googleUser.email").value("john.doe@gmail.com"))
                .andExpect(jsonPath("$.googleUser.name").value("John Doe"))
                .andExpect(jsonPath("$.googleUser.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.googleUser.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty());
    }

    @Order(9)
    @Test
    void getCurrentUser_ReturnsUserProfile_IncludeBooks() throws Exception {
        mockMvc.perform(get("/api/v1/users/me")
                        .param("include-books", "true")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.googleUser.id").value("john.doe"))
                .andExpect(jsonPath("$.googleUser.email").value("john.doe@gmail.com"))
                .andExpect(jsonPath("$.googleUser.name").value("John Doe"))
                .andExpect(jsonPath("$.googleUser.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.googleUser.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.favoriteBooks.size()").value(2))
                .andExpect(jsonPath("$.favoriteBooks[*].title").isNotEmpty())
                .andExpect(jsonPath("$.favoriteBooks[*].author").isNotEmpty())
                .andExpect(jsonPath("$.favoriteBooks[*].publisher").isNotEmpty())
                .andExpect(jsonPath("$.favoriteBooks[*].category").isNotEmpty())
                .andExpect(jsonPath("$.favoriteBooks[*].synopsis").isNotEmpty())
                .andExpect(jsonPath("$.favoriteBooks[*].pages").isNotEmpty())
                .andExpect(jsonPath("$.favoriteBooks[*].releaseDate").isNotEmpty())
                .andExpect(jsonPath("$.favoriteBooks[*].coverImageUrl").isNotEmpty())
                .andExpect(jsonPath("$.favoriteBooks[*].epubUrl").isNotEmpty())
                .andExpect(jsonPath("$.favoriteBooks[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.favoriteBooks[*].updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.shelfBooks.size()").value(1))
                .andExpect(jsonPath("$.shelfBooks[*].title").isNotEmpty())
                .andExpect(jsonPath("$.shelfBooks[*].author").isNotEmpty())
                .andExpect(jsonPath("$.shelfBooks[*].publisher").isNotEmpty())
                .andExpect(jsonPath("$.shelfBooks[*].category").isNotEmpty())
                .andExpect(jsonPath("$.shelfBooks[*].synopsis").isNotEmpty())
                .andExpect(jsonPath("$.shelfBooks[*].pages").isNotEmpty())
                .andExpect(jsonPath("$.shelfBooks[*].releaseDate").isNotEmpty())
                .andExpect(jsonPath("$.shelfBooks[*].coverImageUrl").isNotEmpty())
                .andExpect(jsonPath("$.shelfBooks[*].epubUrl").isNotEmpty())
                .andExpect(jsonPath("$.shelfBooks[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.shelfBooks[*].updatedAt").isNotEmpty());
    }

    @Order(10)
    @Test
    void addFavoriteBook_ReturnsNoContent() throws Exception {
        mockMvc.perform(post("/api/v1/users/books/favorite/3")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Order(11)
    @Test
    void removeFavoriteBook_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/users/books/favorite/1")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Order(12)
    @Test
    void addBookToShelf_ReturnsNoContent() throws Exception {
        mockMvc.perform(post("/api/v1/users/books/shelf/1")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Order(13)
    @Test
    void removeBookFromShelf_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/users/books/shelf/3")
                        .header(X_API_KEY, properties.getApiKey())
                        .with(authentication(SecurityContextHolder.getContext().getAuthentication()))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}