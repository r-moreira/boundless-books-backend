package com.extension.project.boundlessbooks.mapper;

import com.extension.project.boundlessbooks.enums.BookCategory;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.dto.GoogleUserDto;
import com.extension.project.boundlessbooks.model.dto.UserProfileDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import com.extension.project.boundlessbooks.model.entity.GoogleUser;
import com.extension.project.boundlessbooks.model.entity.UserProfile;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserProfileMapperTest {

    private final UserProfileMapper mapper = UserProfileMapper.INSTANCE;

    @Test
    void toDto_MapsEntityToDtoCorrectly() {
        LocalDateTime createdAt = LocalDateTime.parse("2025-05-02T23:20:13.18641", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime updatedAt = LocalDateTime.parse("2025-05-07T22:55:03.095056", DateTimeFormatter.ISO_DATE_TIME);

        GoogleUser googleUser = new GoogleUser();
        googleUser.setId("1234567890");
        googleUser.setEmail("user@example.com");
        googleUser.setName("John Doe");
        googleUser.setCreatedAt(createdAt);
        googleUser.setUpdatedAt(updatedAt);

        BookMetadata book1 = new BookMetadata();
        book1.setId(1L);
        book1.setTitle("Book 1");
        book1.setCategory(BookCategory.FANTASIA.getDisplayName());

        BookMetadata book2 = new BookMetadata();
        book2.setId(2L);
        book2.setTitle("Book 2");
        book2.setCategory(BookCategory.FICCAO_CIENTIFICA.getDisplayName());

        UserProfile userProfile = new UserProfile();
        userProfile.setId(42L);
        userProfile.setGoogleUser(googleUser);
        userProfile.setFavoriteBooks(List.of(book1, book2));
        userProfile.setShelfBooks(List.of(book2));
        userProfile.setCreatedAt(createdAt);
        userProfile.setUpdatedAt(updatedAt);

        UserProfileDto dto = mapper.toDto(userProfile);

        assertEquals(userProfile.getId(), dto.id());
        assertEquals(userProfile.getGoogleUser().getId(), dto.googleUser().id());
        assertEquals(userProfile.getFavoriteBooks().size(), dto.favoriteBooks().size());
        assertEquals(userProfile.getShelfBooks().size(), dto.shelfBooks().size());
        assertEquals(createdAt.format(DateTimeFormatter.ISO_DATE_TIME), dto.createdAt());
        assertEquals(updatedAt.format(DateTimeFormatter.ISO_DATE_TIME), dto.updatedAt());
    }

    @Test
    void toEntity_MapsDtoToEntityCorrectly() {
        String createdAt = "2025-05-02T23:20:13.18641";
        String updatedAt = "2025-05-07T22:55:03.095056";

        GoogleUserDto googleUserDto = new GoogleUserDto(
                "1234567890",
                "user@example.com",
                "John Doe",
                createdAt,
                updatedAt
        );

        BookMetadataDto book1 = new BookMetadataDto(1L, "Book 1", null, null, BookCategory.FANTASIA, null, 0, null, null, null, null, null);
        BookMetadataDto book2 = new BookMetadataDto(2L, "Book 2", null, null, BookCategory.FICCAO_CIENTIFICA, null, 0, null, null, null, null, null);

        UserProfileDto dto = new UserProfileDto(
                42L,
                List.of(book1, book2),
                List.of(book2),
                googleUserDto,
                createdAt,
                updatedAt
        );

        UserProfile entity = mapper.toEntity(dto);

        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.googleUser().id(), entity.getGoogleUser().getId());
        assertEquals(dto.favoriteBooks().size(), entity.getFavoriteBooks().size());
        assertEquals(dto.shelfBooks().size(), entity.getShelfBooks().size());
        assertEquals(LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME), entity.getCreatedAt());
        assertEquals(LocalDateTime.parse(updatedAt, DateTimeFormatter.ISO_DATE_TIME), entity.getUpdatedAt());
    }

    @Test
    void toDtoWithoutBooks_MapsEntityToDtoWithoutBooksCorrectly() {
        LocalDateTime createdAt = LocalDateTime.parse("2025-05-02T23:20:13.18641", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime updatedAt = LocalDateTime.parse("2025-05-07T22:55:03.095056", DateTimeFormatter.ISO_DATE_TIME);

        GoogleUser googleUser = new GoogleUser();
        googleUser.setId("1234567890");
        googleUser.setEmail("user@example.com");
        googleUser.setName("John Doe");
        googleUser.setCreatedAt(createdAt);
        googleUser.setUpdatedAt(updatedAt);

        BookMetadata book1 = new BookMetadata();
        book1.setId(1L);
        book1.setTitle("Book 1");
        book1.setCategory(BookCategory.FANTASIA.getDisplayName());

        BookMetadata book2 = new BookMetadata();
        book2.setId(2L);
        book2.setTitle("Book 2");
        book2.setCategory(BookCategory.FICCAO_CIENTIFICA.getDisplayName());

        UserProfile userProfile = new UserProfile();
        userProfile.setId(42L);
        userProfile.setGoogleUser(googleUser);
        userProfile.setCreatedAt(createdAt);
        userProfile.setUpdatedAt(updatedAt);
        userProfile.setFavoriteBooks(List.of(book1, book2));
        userProfile.setShelfBooks(List.of(book2));


        UserProfileDto dto = mapper.toDtoWithoutBooks(userProfile);

        assertEquals(userProfile.getId(), dto.id());
        assertEquals(userProfile.getGoogleUser().getId(), dto.googleUser().id());
        assertNull(dto.favoriteBooks());
        assertNull(dto.shelfBooks());
        assertEquals(createdAt.format(DateTimeFormatter.ISO_DATE_TIME), dto.createdAt());
        assertEquals(updatedAt.format(DateTimeFormatter.ISO_DATE_TIME), dto.updatedAt());
    }
}