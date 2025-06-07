package com.extension.project.boundlessbooks.service;

import com.extension.project.boundlessbooks.enums.BookCategory;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.dto.BooksMetrics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface BooksService {

    List<BookMetadataDto> getAllBooks(String title, String author, BookCategory category, Date releaseDate);

    Page<BookMetadataDto> getAllBooksPaginated(String title, String author, BookCategory category, Date releaseDate, Pageable pageable);

    BookMetadataDto getBookById(Long id);

    BookMetadataDto createBook(BookMetadataDto bookMetadataDto);

    List<BookMetadataDto> createBooks(List<BookMetadataDto> bookMetadataDto);

    BookMetadataDto updateBook(Long id, BookMetadataDto bookMetadataDto);

    void deleteBook(Long id);

    void deleteAllBooks();

    List<BooksMetrics> getBooksMetrics(BookCategory category, String author);

    Page<BooksMetrics> getBooksMetricsPaginated(BookCategory category, String author, Pageable pageable);
}
