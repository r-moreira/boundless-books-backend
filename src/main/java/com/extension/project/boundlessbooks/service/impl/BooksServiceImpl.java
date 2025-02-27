package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.exception.NotFoundException;
import com.extension.project.boundlessbooks.mapper.BooksMapper;
import com.extension.project.boundlessbooks.mapper.UserProfileMapper;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import com.extension.project.boundlessbooks.repository.BooksRepository;
import com.extension.project.boundlessbooks.repository.UserProfileRepository;
import com.extension.project.boundlessbooks.service.BooksService;
import com.extension.project.boundlessbooks.service.UserProfilesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;
    private final UserProfileRepository userProfileRepository;

    public List<BookMetadataDto> getAllBooks() {
        log.info("Fetching all books");

        return booksRepository.findAll()
                .stream()
                .map(BooksMapper.INSTANCE::toDto)
                .toList();
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