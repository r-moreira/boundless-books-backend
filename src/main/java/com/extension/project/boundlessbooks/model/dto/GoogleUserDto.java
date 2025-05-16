package com.extension.project.boundlessbooks.model.dto;

import com.extension.project.boundlessbooks.model.entity.GoogleUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * DTO for {@link GoogleUser}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GoogleUserDto(
        @Schema(description = "The Google user ID", example = "1234567890", accessMode = Schema.AccessMode.READ_ONLY)
        String id,
        @Schema(description = "The Google user email", example = "user@example.com")
        String email,
        @Schema(description = "The Google user name", example = "John Doe")
        String name,
        @Schema(description = "The date of Google user creation in ISO UTC-8601 format", example = "2025-05-02T23:20:13.18641")
        String createdAt,
        @Schema(description = "The date of Google user update in ISO UTC-8601 format", example = "2025-05-07T22:55:03.095056")
        String updatedAt
) implements Serializable {}