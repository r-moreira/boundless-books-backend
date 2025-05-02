package com.extension.project.boundlessbooks.model.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record MessageDto(
        @NotNull
        String message
) implements Serializable {}
