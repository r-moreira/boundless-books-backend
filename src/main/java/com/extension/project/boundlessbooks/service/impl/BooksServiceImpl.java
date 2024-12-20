package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.exception.NotFoundException;
import com.extension.project.boundlessbooks.mapper.BooksMapper;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.repository.BooksRepository;
import com.extension.project.boundlessbooks.service.BooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;

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

    public boolean deleteBook(Long id) {
        log.info("Deleting book with id: {}", id);

        booksRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        booksRepository.deleteById(id);
        return true;
    }
}