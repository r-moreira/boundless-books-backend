package com.extension.project.boundlessbooks.model.dto;

import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link BookMetadata}
 */
public record BookMetadataDto(
        @Nullable
        Long id,
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotBlank
        String publisher,
        @NotBlank
        String description
) implements Serializable {}