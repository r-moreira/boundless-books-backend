package com.extension.project.boundlessbooks.controller;

import com.extension.project.boundlessbooks.model.dto.UserProfileDto;
import com.extension.project.boundlessbooks.service.UserProfilesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserProfilesService userProfilesService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getCurrentUser(@AuthenticationPrincipal OidcUser oidcUser) {
        UserProfileDto userProfileDto = userProfilesService.getUserProfileById(
                oidcUser.getAttributes().get("sub").toString(),
                oidcUser.getAttributes().get("iss").toString()
        );

        return ResponseEntity.ok().body(userProfileDto);
    }

    @PostMapping("/books/favorite/{bookId}")
    public ResponseEntity<Void> addFavoriteBook(@AuthenticationPrincipal OidcUser oidcUser, @PathVariable Long bookId) {
        userProfilesService.addFavoriteBook(
                oidcUser.getAttributes().get("sub").toString(),
                bookId
        );

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/books/shelf/{bookId}")
    public ResponseEntity<Void> addBookToShelf(@AuthenticationPrincipal OidcUser oidcUser, @PathVariable Long bookId) {
        userProfilesService.addBookToShelf(
                oidcUser.getAttributes().get("sub").toString(),
                bookId
        );

        return ResponseEntity.noContent().build();
    }
}
