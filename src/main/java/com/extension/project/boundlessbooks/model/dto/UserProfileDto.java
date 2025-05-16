package com.extension.project.boundlessbooks.model.dto;

import com.extension.project.boundlessbooks.model.entity.UserProfile;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link UserProfile}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserProfileDto(
        Long id,
        List<BookMetadataDto> favoriteBooks,
        List<BookMetadataDto> shelfBooks,
        GoogleUserDto googleUser,
        @Schema(description = "The date of user profile creation in ISO UTC-8601 format", example = "2025-05-02T23:20:13.18641")
        String createdAt,
        @Schema(description = "The date of user profile update in ISO UTC-8601 format", example = "2025-05-07T22:55:03.095056")
        String updatedAt
) implements Serializable {}