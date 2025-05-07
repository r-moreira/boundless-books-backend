package com.extension.project.boundlessbooks.controller;

import com.extension.project.boundlessbooks.enums.BookCategory;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.service.BooksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;

    @GetMapping
    public List<BookMetadataDto> getAllBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "category", required = false) BookCategory category,
            @RequestParam(value = "release-date", required = false) Date releaseDate
    ) {
        return booksService.getAllBooks(title, author, category, releaseDate);
    }

    @GetMapping("/paginated")
    public Page<BookMetadataDto> getAllBooksPaginated(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "category", required = false) BookCategory category,
            @RequestParam(value = "release-date", required = false) Date releaseDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return booksService.getAllBooksPaginated(title, author, category, releaseDate, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookMetadataDto> getBookById(@PathVariable Long id) {
        BookMetadataDto book = booksService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public BookMetadataDto createBook(@Valid @RequestBody BookMetadataDto bookMetadataDto) {
        return booksService.createBook(bookMetadataDto);
    }


    @PostMapping("/bulk")
    public List<BookMetadataDto> createBooks(@Valid @RequestBody List<BookMetadataDto> bookMetadataDto) {
        return booksService.createBooks(bookMetadataDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookMetadataDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookMetadataDto bookMetadataDto) {
        BookMetadataDto updatedBook = booksService.updateBook(id, bookMetadataDto);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        booksService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteBooks() {
        booksService.deleteAllBooks();
        return ResponseEntity.noContent().build();
    }
}