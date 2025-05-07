package com.extension.project.boundlessbooks.controller;

import com.extension.project.boundlessbooks.model.dto.MessageDto;
import com.extension.project.boundlessbooks.model.dto.UserProfileDto;
import com.extension.project.boundlessbooks.service.UserProfilesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserProfilesService userProfilesService;

    @GetMapping("/search")
    public ResponseEntity<List<UserProfileDto>> getAllUsers(
            @RequestParam(value = "include-books", defaultValue = "false") boolean includeBooks,
            @RequestParam(value = "name", required = false) String name
    ) {
        List<UserProfileDto> userProfiles = userProfilesService.getAllUserProfiles(includeBooks, name);
        return ResponseEntity.ok().body(userProfiles);
    }

    @GetMapping("/search/paginated")
    public Page<UserProfileDto> getAllUsers(
            @RequestParam(value = "include-books", defaultValue = "false") boolean includeBooks,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userProfilesService.getAllUserProfilesPaginated(includeBooks, name, pageable);
    }

    @GetMapping("/validate-login")
    public ResponseEntity<MessageDto> validateLogin(@AuthenticationPrincipal OidcUser oidcUser) {
        log.info("Validating login for user: {}", oidcUser.getEmail());
        return ResponseEntity.ok().body(new MessageDto("User is logged in"));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getCurrentUser(
            @AuthenticationPrincipal OidcUser oidcUser,
            @RequestParam(value = "include-books", defaultValue = "true") boolean includeBooks) {
        UserProfileDto userProfileDto = userProfilesService.getUserProfileById(
                oidcUser.getAttributes().get("sub").toString(),
                oidcUser.getAttributes().get("iss").toString(),
                includeBooks
        );

        return ResponseEntity.ok().body(userProfileDto);
    }

    @PostMapping("/books/favorite/{bookId}")
    public ResponseEntity<Void> addFavoriteBook(
            @AuthenticationPrincipal OidcUser oidcUser,
            @PathVariable Long bookId) {
        userProfilesService.addFavoriteBook(
                oidcUser.getAttributes().get("sub").toString(),
                bookId
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/books/favorite/{bookId}")
    public ResponseEntity<Void> removeFavoriteBook(
            @AuthenticationPrincipal OidcUser oidcUser,
            @PathVariable Long bookId) {
        userProfilesService.removeFavoriteBook(
                oidcUser.getAttributes().get("sub").toString(),
                bookId
        );

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/books/shelf/{bookId}")
    public ResponseEntity<Void> addBookToShelf(
            @AuthenticationPrincipal OidcUser oidcUser,
            @PathVariable Long bookId) {
        userProfilesService.addBookToShelf(
                oidcUser.getAttributes().get("sub").toString(),
                bookId
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/books/shelf/{bookId}")
    public ResponseEntity<Void> removeBookFromShelf(
            @AuthenticationPrincipal OidcUser oidcUser,
            @PathVariable Long bookId) {
        userProfilesService.removeBookFromShelf(
                oidcUser.getAttributes().get("sub").toString(),
                bookId
        );

        return ResponseEntity.noContent().build();
    }
}
