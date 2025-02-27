package com.extension.project.boundlessbooks.service;

import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;

import java.util.List;

public interface BooksService {

    List<BookMetadataDto> getAllBooks();

    BookMetadataDto getBookById(Long id);

    BookMetadataDto createBook(BookMetadataDto bookMetadataDto);

    List<BookMetadataDto> createBooks(List<BookMetadataDto> bookMetadataDto);

    BookMetadataDto updateBook(Long id, BookMetadataDto bookMetadataDto);

    void deleteBook(Long id);

    void deleteAllBooks();
}