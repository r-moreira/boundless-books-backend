package com.extension.project.boundlessbooks.model.dto;

import com.extension.project.boundlessbooks.model.entity.UserProfile;

import java.io.Serializable;

/**
 * DTO for {@link UserProfile}
 */
public record UserProfileDto(
        String id,
        GoogleUserDto googleUser
) implements Serializable {}