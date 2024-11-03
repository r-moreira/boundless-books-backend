package com.extension.project.boundlessbooks.model.dto;

import com.extension.project.boundlessbooks.model.entity.GoogleUser;

import java.io.Serializable;

/**
 * DTO for {@link GoogleUser}
 */
public record GoogleUserDto(
        String id,
        String email,
        String name
) implements Serializable {}