package com.extension.project.boundlessbooks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "OAuth2 Authentication", description = "Operations related to OAuth2 authentication")
public class OAuth2Controller {

    @GetMapping("/oauth2/authorization/google")
    @Operation(summary = "Redirects to Google OAuth2 login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirect to Google OAuth2 login page")
    })
    public void googleLogin() {
        // This route is handled by Spring Security, no implementation needed
    }

    @GetMapping("/logout")
    @Operation(summary = "Logs out the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirect to logout endpoint")
    })
    public void logout() {
        // This route is handled by Spring Security, no implementation needed
    }
}