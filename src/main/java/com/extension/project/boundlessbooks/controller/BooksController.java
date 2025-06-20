package com.extension.project.boundlessbooks.controller;

import com.extension.project.boundlessbooks.enums.BookCategory;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.dto.BookMetrics;
import com.extension.project.boundlessbooks.service.BooksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@SecurityRequirement(name = "Application Key")
@Tag(name = "Books Metadata Management", description = "Operations related to books metadata")
public class BooksController {

    private final BooksService booksService;

    @GetMapping
    @Operation(summary = "Returns all books metadata")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books Metadata List", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = BookMetadataDto.class))),
            })
    })
    @Parameters({
            @Parameter(name = "title", description = "The title of the book", example = "Harry Potter e a Ordem da Fênix"),
            @Parameter(name = "author", description = "The author of the book", example = "J.K. Rowling"),
            @Parameter(name = "category", description = "The category of the book", example = "Fantasia"),
            @Parameter(name = "release-date", description = "The release date of the book in yyyy/MM/dd format", example = "2003/06/20")
    })
    public ResponseEntity<List<BookMetadataDto>> getAllBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "category", required = false) BookCategory category,
            @DateTimeFormat(pattern = "yyyy/MM/dd") @Valid
            @RequestParam(value = "release-date", required = false) Date releaseDate) {
        List<BookMetadataDto> books = booksService.getAllBooks(title, author, category, releaseDate);
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Returns all books metadata paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books Metadata Paged", useReturnTypeSchema = true)
    })
    @Parameters({
            @Parameter(name = "title", description = "The title of the book", example = "Harry Potter e a Ordem da Fênix"),
            @Parameter(name = "author", description = "The author of the book", example = "J.K. Rowling"),
            @Parameter(name = "category", description = "The category of the book", example = "Fantasia"),
            @Parameter(name = "release-date", description = "The release date of the book in yyyy/MM/dd format", example = "2003/06/20"),
            @Parameter(name = "page", description = "Page number to retrieve", example = "0"),
            @Parameter(name = "size", description = "Number of items per page", example = "10")
    })
    public ResponseEntity<Page<BookMetadataDto>> getAllBooksPaginated(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "category", required = false) BookCategory category,
            @DateTimeFormat(pattern = "yyyy/MM/dd") @Valid
            @RequestParam(value = "release-date", required = false) Date releaseDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookMetadataDto> books = booksService.getAllBooksPaginated(title, author, category, releaseDate, pageable);
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a book metadata by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book Metadata", content = {
                    @Content(schema = @Schema(implementation = BookMetadataDto.class)),
            }),
    })
    @Parameters({
            @Parameter(name = "id", description = "The id of the book", example = "42")
    })
    public ResponseEntity<BookMetadataDto> getBookById(@PathVariable Long id) {
        BookMetadataDto book = booksService.getBookById(id);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping
    @Operation(summary = "Creates a new book metadata")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book Metadata Created", content = {
                    @Content(schema = @Schema(implementation = BookMetadataDto.class)),
            }),
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
            @Content(schema = @Schema(implementation = BookMetadataDto.class)),
    })
    public ResponseEntity<BookMetadataDto> createBook(@Valid @RequestBody BookMetadataDto bookMetadataDto) {
        BookMetadataDto createdBook = booksService.createBook(bookMetadataDto);
        return ResponseEntity.status(201).body(createdBook);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Creates multiple new book metadata")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Books Metadata Created", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = BookMetadataDto.class))),
            })
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = BookMetadataDto.class))),
    })
    public ResponseEntity<List<BookMetadataDto>> createBooks(@Valid @RequestBody List<BookMetadataDto> bookMetadataDto) {
        List<BookMetadataDto> createdBooks = booksService.createBooks(bookMetadataDto);
        return ResponseEntity.status(201).body(createdBooks);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a book metadata by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book Metadata Updated", content = {
                    @Content(schema = @Schema(implementation = BookMetadataDto.class)),
            }),
    })
    @Parameters({
            @Parameter(name = "id", description = "The id of the book", example = "42")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
            @Content(schema = @Schema(implementation = BookMetadataDto.class)),
    })
    public ResponseEntity<BookMetadataDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookMetadataDto bookMetadataDto) {
        BookMetadataDto updatedBook = booksService.updateBook(id, bookMetadataDto);
        return ResponseEntity.ok().body(updatedBook);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a book metadata by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book Metadata Deleted"),
    })
    @Parameters({
            @Parameter(name = "id", description = "The id of the book", example = "42")
    })
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        booksService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/bulk")
    @Operation(summary = "Deletes all book metadata")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All Books Metadata Deleted"),
    })
    public ResponseEntity<Void> deleteBooks() {
        booksService.deleteAllBooks();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/metrics")
    @Operation(summary = "Returns books metrics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books metrics retrieved", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = BookMetrics.class)))
            })
    })
    @Parameters({
            @Parameter(name = "category", description = "Filter by book category", example = "Fantasia"),
            @Parameter(name = "author", description = "Filter by book author", example = "J.K. Rowling")
    })
    public ResponseEntity<List<BookMetrics>> getBooksMetrics(
            @RequestParam(value = "category", required = false) BookCategory category,
            @RequestParam(value = "author", required = false) String author) {
        List<BookMetrics> metrics = booksService.getBooksMetrics(category, author);
        return ResponseEntity.ok().body(metrics);
    }

    @GetMapping("/metrics/paginated")
    @Operation(summary = "Returns paginated books metrics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated books metrics retrieved", useReturnTypeSchema = true)
    })
    @Parameters({
            @Parameter(name = "category", description = "Filter by book category", example = "Fantasia"),
            @Parameter(name = "author", description = "Filter by book author", example = "J.K. Rowling"),
            @Parameter(name = "page", description = "Page number to retrieve", example = "0"),
            @Parameter(name = "size", description = "Number of items per page", example = "10")
    })
    public ResponseEntity<Page<BookMetrics>> getBooksMetricsPaginated(
            @RequestParam(value = "category", required = false) BookCategory category,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookMetrics> metrics = booksService.getBooksMetricsPaginated(category, author, pageable);
        return ResponseEntity.ok().body(metrics);
    }
}