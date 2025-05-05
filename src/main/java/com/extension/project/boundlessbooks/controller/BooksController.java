package com.extension.project.boundlessbooks.controller;

import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.service.BooksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;

    //TODO:
    // Implementar paginação
    // Implementar index de busca para title e author (%like%)
    // Implementar filtro por título
    // Implementar filtro por autor
    // Implementar filtro por categoria
    // Implementar filtro por data de lançamento
    @GetMapping
    public List<BookMetadataDto> getAllBooks() {
        return booksService.getAllBooks();
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