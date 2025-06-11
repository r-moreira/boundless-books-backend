package com.extension.project.boundlessbooks.model.dto;

import com.extension.project.boundlessbooks.enums.InteractionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Represents an interaction event")
public record InteractionEvent(
        @Schema(description = "Unique identifier for the process", example = "45a2932d-8709-4e10-ba94-6e5c7a18b6f3")
        String processId,

        @Schema(description = "ID of the book involved in the interaction", example = "42")
        String bookId,

        @Schema(description = "ID of the user performing the interaction", example = "27")
        String userId,

        @Schema(description = "Type of interaction", example = "read_start")
        InteractionType interactionType,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
        Date timestamp
) {
}