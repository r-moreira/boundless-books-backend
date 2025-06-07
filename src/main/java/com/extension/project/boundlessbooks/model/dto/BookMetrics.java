package com.extension.project.boundlessbooks.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)

public record BookMetricsDto(
        @Schema(description = "The count of how many times the book was added to the users favorite list", example = "150")
        @NotNull
        Integer favoriteCount,
        @Schema(description = "The count of how many times the book was added to the users shelf", example = "300")
        @NotNull
        Integer shelfCount,
        @NotNull
        @Schema(description = "The book metadata", implementation = BookMetadataDto.class)
        BookMetadataDto book
) {}
