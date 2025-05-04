package com.extension.project.boundlessbooks.model.dto;

import com.extension.project.boundlessbooks.model.entity.UserProfile;
import com.fasterxml.jackson.annotation.JsonInclude;

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
        String createdAt,
        String updatedAt
) implements Serializable {}