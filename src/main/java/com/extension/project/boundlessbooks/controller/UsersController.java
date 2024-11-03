package com.extension.project.boundlessbooks.controller;

import com.extension.project.boundlessbooks.model.dto.UserProfileDto;
import com.extension.project.boundlessbooks.service.UserProfilesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserProfilesService userProfilesService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getCurrentUser(@AuthenticationPrincipal OidcUser oidcUser) {
        log.info("OidcUser: {}", oidcUser.toString());

        UserProfileDto userProfileDto = userProfilesService.getUserProfileById(oidcUser.getAttributes().get("sub").toString());

        return ResponseEntity.ok().body(userProfileDto);
    }
}
