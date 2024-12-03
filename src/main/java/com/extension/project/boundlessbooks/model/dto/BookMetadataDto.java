package com.extension.project.boundlessbooks.model.dto;

import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

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
        String category,
        @NotBlank
        String synopsis,
        @JsonFormat(pattern = "yyyy/MM/dd")
        Date releaseDate,
        @NotBlank
        String coverImageUrl,
        @NotBlank
        String epubUrl,
        String createdAt,
        String updatedAt
) implements Serializable {}