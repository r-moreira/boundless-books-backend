package com.extension.project.boundlessbooks.mapper;

import com.extension.project.boundlessbooks.enums.BookCategory;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BooksMapperTest {

    private final BooksMapper mapper = BooksMapper.INSTANCE;

    @Test
    void toDto_MapsEntityToDtoCorrectly() {
        BookMetadata bookMetadata = new BookMetadata();
        bookMetadata.setId(1L);
        bookMetadata.setTitle("Test Title");
        bookMetadata.setAuthor("Test Author");
        bookMetadata.setPublisher("Test Publisher");
        bookMetadata.setCategory(BookCategory.FANTASIA.getDisplayName());
        bookMetadata.setSynopsis("Test Synopsis");
        bookMetadata.setPages(300);
        bookMetadata.setReleaseDate(new Date());
        bookMetadata.setCoverImageUrl("http://example.com/cover.jpg");
        bookMetadata.setEpubUrl("http://example.com/book.epub");

        BookMetadataDto dto = mapper.toDto(bookMetadata);

        assertEquals(bookMetadata.getId(), dto.id());
        assertEquals(bookMetadata.getTitle(), dto.title());
        assertEquals(bookMetadata.getAuthor(), dto.author());
        assertEquals(bookMetadata.getPublisher(), dto.publisher());
        assertEquals(BookCategory.FANTASIA, dto.category());
        assertEquals(bookMetadata.getSynopsis(), dto.synopsis());
        assertEquals(bookMetadata.getPages(), dto.pages());
        assertEquals(bookMetadata.getReleaseDate(), dto.releaseDate());
        assertEquals(bookMetadata.getCoverImageUrl(), dto.coverImageUrl());
        assertEquals(bookMetadata.getEpubUrl(), dto.epubUrl());
    }

    @Test
    void toEntity_MapsDtoToEntityCorrectly() {
        BookMetadataDto dto = new BookMetadataDto(
                null,
                "Test Title",
                "Test Author",
                "Test Publisher",
                BookCategory.FANTASIA,
                "Test Synopsis",
                300,
                new Date(),
                "http://example.com/cover.jpg",
                "http://example.com/book.epub",
                null,
                null
        );

        BookMetadata entity = mapper.toEntity(dto);

        assertNull(entity.getId());
        assertEquals(dto.title(), entity.getTitle());
        assertEquals(dto.author(), entity.getAuthor());
        assertEquals(dto.publisher(), entity.getPublisher());
        assertEquals(dto.category().getDisplayName(), entity.getCategory());
        assertEquals(dto.synopsis(), entity.getSynopsis());
        assertEquals(dto.pages(), entity.getPages());
        assertEquals(dto.releaseDate(), entity.getReleaseDate());
        assertEquals(dto.coverImageUrl(), entity.getCoverImageUrl());
        assertEquals(dto.epubUrl(), entity.getEpubUrl());
    }

    @Test
    void toIdentifiedEntity_MapsDtoToEntityWithIdCorrectly() {
        BookMetadataDto dto = new BookMetadataDto(
                null,
                "Test Title",
                "Test Author",
                "Test Publisher",
                BookCategory.FANTASIA,
                "Test Synopsis",
                300,
                new Date(),
                "http://example.com/cover.jpg",
                "http://example.com/book.epub",
                null,
                null
        );

        Long id = 42L;
        BookMetadata entity = mapper.toIdentifiedEntity(dto, id);

        assertEquals(id, entity.getId());
        assertEquals(dto.title(), entity.getTitle());
        assertEquals(dto.author(), entity.getAuthor());
        assertEquals(dto.publisher(), entity.getPublisher());
        assertEquals(dto.category().getDisplayName(), entity.getCategory());
        assertEquals(dto.synopsis(), entity.getSynopsis());
        assertEquals(dto.pages(), entity.getPages());
        assertEquals(dto.releaseDate(), entity.getReleaseDate());
        assertEquals(dto.coverImageUrl(), entity.getCoverImageUrl());
        assertEquals(dto.epubUrl(), entity.getEpubUrl());
    }
}