package com.extension.project.boundlessbooks.controller;

import com.extension.project.boundlessbooks.annotation.SessionCookieParameter;
import com.extension.project.boundlessbooks.annotation.ValidateOidcUser;
import com.extension.project.boundlessbooks.enums.InteractionType;
import com.extension.project.boundlessbooks.model.dto.InteractionResponse;
import com.extension.project.boundlessbooks.service.InteractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/interactions")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Application Key")
@Tag(name = "Interactions", description = "Operations related to user interactions with books")
public class InteractionController {

    private final InteractionService interactionService;

    @PostMapping("/{interactionType}/{bookId}")
    @SessionCookieParameter
    @ValidateOidcUser
    @Operation(summary = "Processes a user interaction with a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interaction processed successfully", content = {
                    @Content(schema = @Schema(implementation = InteractionResponse.class))
            })
    })
    @Parameters({
            @Parameter(name = "interactionType", description = "Type of interaction (e.g., LIKE, DISLIKE)", example = "LIKE"),
            @Parameter(name = "bookId", description = "ID of the book to interact with", example = "42")
    })
    public ResponseEntity<InteractionResponse> processInteraction(
            @AuthenticationPrincipal OidcUser oidcUser,
            @PathVariable Long bookId,
            @PathVariable InteractionType interactionType
    ) {
        var response = interactionService.sendInteractionEvent(
                bookId,
                oidcUser.getAttributes().get("sub").toString(),
                interactionType
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}