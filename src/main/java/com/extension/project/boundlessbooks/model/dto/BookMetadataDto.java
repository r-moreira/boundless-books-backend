package com.extension.project.boundlessbooks.model.dto;

import com.extension.project.boundlessbooks.enums.BookCategory;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link BookMetadata}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookMetadataDto(
        @Schema(description = "The book id", example = "42", accessMode = Schema.AccessMode.READ_ONLY)
        @Nullable
        Long id,
        @Schema(description = "The book title", example = "Harry Potter e a Ordem da Fênix")
        @NotBlank
        String title,
        @Schema(description = "The book author", example = "J.K. Rowling")
        @NotBlank
        String author,
        @Schema(description = "The book publisher", example = "Rocco")
        @NotBlank
        String publisher,
        @Schema(description = "The book category", example = "Fantasia")
        @NotNull
        BookCategory category,
        @Schema(description = "The book synopsis", example = "Harry Potter retorna para seu quinto ano em Hogwarts e descobre que o Ministério da Magia está em negação sobre o retorno de Voldemort.")
        @NotBlank
        String synopsis,
        @Schema(description = "The number of pages in the book", example = "704")
        @NotNull
        Integer pages,
        @Schema(description = "The book release date, must follow the pattern yyyy/MM/dd", example = "2003/06/20")
        @JsonFormat(pattern = "yyyy/MM/dd")
        Date releaseDate,
        @NotBlank
        @Schema(description = "The book cover image url", example = "https://ik.imagekit.io/boundlessbooks/cover-images/cover_avHr99l5G.jpg")
        String coverImageUrl,
        @NotBlank
        @Schema(description = "The book .epub url", example = "https://ik.imagekit.io/boundlessbooks/epubs/epub_RRU77Qqdh.epub")
        String epubUrl,
        @Schema(description = "The date of book metadata creation in ISO UTC-8601 format", example = "2025-05-02T23:20:13.18641", accessMode = Schema.AccessMode.READ_ONLY)
        String createdAt,
        @Schema(description = "The date of book metadata update in ISO UTC-8601 format", example = "2025-05-07T22:55:03.095056", accessMode = Schema.AccessMode.READ_ONLY)
        String updatedAt
) implements Serializable {}