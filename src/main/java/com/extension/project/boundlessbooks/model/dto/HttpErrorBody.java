package com.extension.project.boundlessbooks.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Represents the structure of an HTTP error response")
public record HttpErrorBody(
        @Schema(description = "Detailed error message", example = "Resource not found", nullable = true)
        @Nullable
        String message,

        @Schema(description = "Error type or code", example = "NOT_FOUND")
        @NotBlank
        String error,

        @Schema(description = "HTTP status code", example = "404")
        @NotBlank
        int status
) implements Serializable {}