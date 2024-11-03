package com.extension.project.boundlessbooks.controller;

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

    @GetMapping("/hello")
    public ResponseEntity<String> getCurrent(@AuthenticationPrincipal OidcUser oidcUser) {
        return ResponseEntity.ok("Hello, " + oidcUser.getFullName());
    }
}
