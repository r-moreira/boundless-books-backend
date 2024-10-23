package com.extension.project.boundlessbooks.model.dto;

import com.extension.project.boundlessbooks.model.entity.BookMetadata;

import java.io.Serializable;

/**
 * DTO for {@link BookMetadata}
 */
public record BookMetadataDto(
        Long id,
        String title,
        String author,
        String publisher,
        String description
) implements Serializable {}