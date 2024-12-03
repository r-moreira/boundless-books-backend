package com.extension.project.boundlessbooks.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record HttpErrorBody(
        @Nullable
        String message,
        @NotBlank
        String error,
        @NotBlank
        int status
) implements Serializable {}
