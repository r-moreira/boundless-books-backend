package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.enums.BookCategory;
import com.extension.project.boundlessbooks.exception.NotFoundException;
import com.extension.project.boundlessbooks.mapper.BooksMapper;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import com.extension.project.boundlessbooks.repository.BooksRepository;
import com.extension.project.boundlessbooks.repository.UserProfileRepository;
import com.extension.project.boundlessbooks.service.BooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;
    private final UserProfileRepository userProfileRepository;

    public List<BookMetadataDto> getAllBooks(String title, String author, BookCategory category, Date releaseDate) {
        var categoryName = category == null ? null : category.getDisplayName();

        log.info("Fetching all books: title={}, author={}, category={}, releaseDate={}", title, author, categoryName, releaseDate);

        return booksRepository.findBooksByFilters(title, author, categoryName, releaseDate)
                .stream()
                .map(BooksMapper.INSTANCE::toDto)
                .toList();
    }

    public Page<BookMetadataDto> getAllBooksPaginated(String title, String author, BookCategory category, Date releaseDate, Pageable pageable) {
        var categoryName = category == null ? null : category.getDisplayName();

        log.info("Fetching paginated books: title={}, author={}, category={}, releaseDate={}, pageable={}", title, author, categoryName, releaseDate, pageable);

        return booksRepository.findBooksByFiltersWithPagination(title, author, categoryName, releaseDate, pageable)
                .map(BooksMapper.INSTANCE::toDto);
    }

    public BookMetadataDto getBookById(Long id) {
        log.info("Fetching book by id: {}", id);

       return booksRepository.findById(id)
                .map(BooksMapper.INSTANCE::toDto)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public BookMetadataDto createBook(BookMetadataDto bookMetadataDto) {
        log.info("Creating book: {}", bookMetadataDto);

        return BooksMapper.INSTANCE.toDto(
                booksRepository.save(
                        BooksMapper.INSTANCE.toEntity(bookMetadataDto)
                )
        );
    }

    @Transactional
    @Override
    public List<BookMetadataDto> createBooks(List<BookMetadataDto> bookMetadataDto) {
        log.info("Creating books: {}", bookMetadataDto.stream().map(BookMetadataDto::title).toList());

        return booksRepository.saveAll(
            bookMetadataDto.stream()
                .map(BooksMapper.INSTANCE::toEntity)
                .toList())
            .stream()
            .map(BooksMapper.INSTANCE::toDto)
            .toList();
    }

    public BookMetadataDto updateBook(Long id, BookMetadataDto bookMetadataDto) {
        log.info("Updating book with id: {}", id);

        booksRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        return BooksMapper.INSTANCE.toDto(
                booksRepository.save(
                        BooksMapper.INSTANCE.toIdentifiedEntity(
                                bookMetadataDto,
                                id
                        )
                )
        );
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        log.info("Deleting book with id: {}", id);

        BookMetadata book = booksRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        book.getFavoriteByUsers().forEach(user -> {
            user.getFavoriteBooks().remove(book);
            userProfileRepository.save(user);
        });

        book.getShelfByUsers().forEach(user -> {
            user.getShelfBooks().remove(book);
            userProfileRepository.save(user);
        });

        booksRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllBooks() {
        log.info("Deleting all books");

        booksRepository.findAll().forEach(book -> {
            book.getFavoriteByUsers().forEach(user -> {
                user.getFavoriteBooks().remove(book);
                userProfileRepository.save(user);
            });

            book.getShelfByUsers().forEach(user -> {
                user.getShelfBooks().remove(book);
                userProfileRepository.save(user);
            });
        });

        booksRepository.deleteAll();
    }


}