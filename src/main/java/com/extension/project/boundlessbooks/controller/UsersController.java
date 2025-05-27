package com.extension.project.boundlessbooks.controller;

import com.extension.project.boundlessbooks.annotation.SessionCookieParameter;
import com.extension.project.boundlessbooks.annotation.ValidateOidcUser;
import com.extension.project.boundlessbooks.model.dto.UserProfileDto;
import com.extension.project.boundlessbooks.service.UserProfilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "Application Key")
@Tag(name = "User Profiles Management", description = "Operations related to user profiles")
public class UsersController {

    private final UserProfilesService userProfilesService;

    @GetMapping("/search")
    @Operation(summary = "Searches for user profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profiles retrieved", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = UserProfileDto.class)))
            })
    })
    @Parameters({
            @Parameter(name = "include-books", description = "Include books favorite books and shelf books in the response", example = "true"),
            @Parameter(name = "name", description = "Filter by user name", example = "John Doe")
    })
    public ResponseEntity<List<UserProfileDto>> getAllUsers(
            @RequestParam(value = "include-books", defaultValue = "false") boolean includeBooks,
            @RequestParam(value = "name", required = false) String name
    ) {
        List<UserProfileDto> userProfiles = userProfilesService.getAllUserProfiles(includeBooks, name);
        return ResponseEntity.ok().body(userProfiles);
    }

    @GetMapping("/search/paginated")
    @Operation(summary = "Searches for user profiles with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated user profiles retrieved", useReturnTypeSchema = true)
    })
    @Parameters({
            @Parameter(name = "include-books", description = "Include books favorite books and shelf books in the response", example = "true"),
            @Parameter(name = "name", description = "Filter by user name", example = "John Doe"),
            @Parameter(name = "page", description = "Page number to retrieve", example = "0"),
            @Parameter(name = "size", description = "Number of items per page", example = "10")
    })
    public Page<UserProfileDto> getAllUsers(
            @RequestParam(value = "include-books", defaultValue = "false") boolean includeBooks,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userProfilesService.getAllUserProfilesPaginated(includeBooks, name, pageable);
    }

    @GetMapping("/me")
    @Operation(summary = "Retrieves the current user's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved", content = {
                    @Content(schema = @Schema(implementation = UserProfileDto.class))
            })
    })
    @Parameters({
            @Parameter(name = "include-books", description = "Include books in the response", example = "true")
    })
    @SessionCookieParameter
    @ValidateOidcUser
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
    @Operation(summary = "Adds a book to the user's favorites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book added to favorites")
    })
    @Parameters({
            @Parameter(name = "bookId", description = "ID of the book to add", example = "42")
    })
    @SessionCookieParameter
    @ValidateOidcUser
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
    @Operation(summary = "Removes a book from the user's favorites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book removed from favorites")
    })
    @Parameters({
            @Parameter(name = "bookId", description = "ID of the book to remove", example = "42")
    })
    @SessionCookieParameter
    @ValidateOidcUser
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
    @Operation(summary = "Adds a book to the user's shelf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book added to shelf")
    })
    @Parameters({
            @Parameter(name = "bookId", description = "ID of the book to add", example = "42")
    })
    @SessionCookieParameter
    @ValidateOidcUser
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
    @Operation(summary = "Removes a book from the user's shelf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book removed from shelf")
    })
    @Parameters({
            @Parameter(name = "bookId", description = "ID of the book to remove", example = "42")
    })
    @SessionCookieParameter
    @ValidateOidcUser
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