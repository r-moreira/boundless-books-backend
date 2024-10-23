package com.extension.project.boundlessbooks.service;

import com.extension.project.boundlessbooks.exception.NotFoundException;
import com.extension.project.boundlessbooks.mapper.BooksMapper;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import com.extension.project.boundlessbooks.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository booksRepository;

    public List<BookMetadataDto> getAllBooks() {
        return booksRepository.findAll()
                .stream()
                .map(BooksMapper.INSTANCE::toDto)
                .toList();
    }

    public BookMetadataDto getBookById(Long id) {
        var book = booksRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        return BooksMapper.INSTANCE.toDto(book);
    }

    public BookMetadataDto createBook(BookMetadataDto bookMetadataDto) {
        return BooksMapper.INSTANCE.toDto(
                booksRepository.save(
                        BooksMapper.INSTANCE.toEntity(bookMetadataDto)
                )
        );
    }

    public BookMetadataDto updateBook(Long id, BookMetadataDto bookMetadataDto) {
        Optional<BookMetadata> optionalBook = booksRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new NotFoundException("Book not found");
        }

        return BooksMapper.INSTANCE.toDto(
                booksRepository.save(
                        BooksMapper.INSTANCE.toEntity(bookMetadataDto
                        )
                )
        );
    }

    public boolean deleteBook(Long id) {
        if (!booksRepository.existsById(id)) {
            throw new NotFoundException("Book not found");
        }

        booksRepository.deleteById(id);
        return true;
    }
}