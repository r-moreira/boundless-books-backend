package com.extension.project.boundlessbooks.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Represents the response for an interaction event")
public record InteractionResponse(
        @Schema(description = "Unique identifier for the process", example = "45a2932d-8709-4e10-ba94-6e5c7a18b6f3")
        @NotBlank String processId
) {}