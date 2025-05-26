package com.extension.project.boundlessbooks.factory;

import com.extension.project.boundlessbooks.enums.BookCategory;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UtilityClass
public class BookMetadataFactory {

    public static BookMetadata createBookMetadata() {
        BookMetadata book = new BookMetadata();
        book.setId(1L);
        book.setTitle("Harry Potter e a Ordem da Fênix");
        book.setAuthor("J.K. Rowling");
        book.setPublisher("Rocco");
        book.setCategory("Fantasia");
        book.setSynopsis("Harry Potter retorna para seu quinto ano em Hogwarts...");
        book.setPages(704);
        book.setReleaseDate(new Date());
        book.setCoverImageUrl("https://example.com/cover.jpg");
        book.setEpubUrl("https://example.com/book.epub");
        book.setFavoriteByUsers(new ArrayList<>(List.of(UserProfileFactory.createUserProfile())));
        book.setShelfByUsers(new ArrayList<>(List.of(UserProfileFactory.createUserProfile())));
        return book;
    }

    public static BookMetadataDto createBookMetadataDto() {
        return new BookMetadataDto(
                1L,
                "Harry Potter e a Ordem da Fênix",
                "J.K. Rowling",
                "Rocco",
                BookCategory.FANTASIA,
                "Harry Potter retorna para seu quinto ano em Hogwarts...",
                704,
                new Date(),
                "https://example.com/cover.jpg",
                "https://example.com/book.epub",
                "2025-05-02T23:20:13.18641",
                "2025-05-07T22:55:03.095056"
        );
    }
}
