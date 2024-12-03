package com.extension.project.boundlessbooks.model.dto;

import com.extension.project.boundlessbooks.model.entity.UserProfile;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link UserProfile}
 */
public record UserProfileDto(
        Long id,
        List<BookMetadataDto> favoriteBooks,
        List<BookMetadataDto> shelfBooks,
        GoogleUserDto googleUser,
        String createdAt,
        String updatedAt
) implements Serializable {}