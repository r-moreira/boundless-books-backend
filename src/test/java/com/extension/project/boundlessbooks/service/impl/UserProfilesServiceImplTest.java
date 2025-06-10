package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.exception.NotFoundException;
import com.extension.project.boundlessbooks.exception.NotImplementedException;
import com.extension.project.boundlessbooks.factory.BookMetadataFactory;
import com.extension.project.boundlessbooks.factory.UserProfileFactory;
import com.extension.project.boundlessbooks.mapper.UserProfileMapper;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.dto.UserProfileDto;
import com.extension.project.boundlessbooks.model.entity.UserProfile;
import com.extension.project.boundlessbooks.repository.UserProfileRepository;
import com.extension.project.boundlessbooks.service.BooksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfilesServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private BooksService booksService;

    @InjectMocks
    private UserProfilesServiceImpl userProfilesService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllUserProfiles_ReturnsProfiles() {
        UserProfile userProfile = UserProfileFactory.createUserProfile();
        when(userProfileRepository.findAll()).thenReturn(List.of(userProfile));

        List<UserProfileDto> result = userProfilesService.getAllUserProfiles(false, null);

        assertEquals(1, result.size());
        verify(userProfileRepository, times(1)).findAll();
    }

    @Test
    void getAllUserProfiles_WithEmptyName_ReturnsProfiles() {
        UserProfile userProfile = UserProfileFactory.createUserProfile();
        when(userProfileRepository.findAll()).thenReturn(List.of(userProfile));

        List<UserProfileDto> result = userProfilesService.getAllUserProfiles(false, "");

        assertEquals(1, result.size());
        verify(userProfileRepository, times(1)).findAll();
    }

    @Test
    void getUserProfileById_ExistingUser_ReturnsProfile() {
        UserProfile userProfile = UserProfileFactory.createUserProfile();
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.of(userProfile));

        UserProfileDto result = userProfilesService.getUserProfileById("12345", "https://accounts.google.com", true);

        assertNotNull(result);
        assertEquals(userProfile.getId(), result.id());
        verify(userProfileRepository, times(1)).findByGoogleUserId("12345");
    }

    @Test
    void getUserProfileById_ExistingUserWithoutBooks_ReturnsProfile() {
        UserProfile userProfile = UserProfileFactory.createUserProfile();
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.of(userProfile));

        UserProfileDto result = userProfilesService.getUserProfileById("12345", "https://accounts.google.com", false);

        assertNotNull(result);
        assertEquals(userProfile.getId(), result.id());
        assertNull(result.favoriteBooks());
        assertNull(result.shelfBooks());
        verify(userProfileRepository, times(1)).findByGoogleUserId("12345");
    }

    @Test
    void getUserProfileById_NonExistingUser_ThrowsNotFoundException() {
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userProfilesService.getUserProfileById("12345", "https://accounts.google.com", true));
        verify(userProfileRepository, times(1)).findByGoogleUserId("12345");
    }

    @Test
    void addFavoriteBook_AddsBookToFavorites() {
        UserProfile userProfile = UserProfileFactory.createUserProfile();
        BookMetadataDto bookDto = BookMetadataFactory.createBookMetadataDto();
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.of(userProfile));
        when(booksService.getBookById(1L)).thenReturn(bookDto);

        userProfilesService.addFavoriteBook("12345", 1L);

        verify(userProfileRepository, times(1)).save(userProfile);
    }

    @Test
    void addFavoriteBook_ThrowsNotFoundException_WhenUserDoNotExist() {
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                userProfilesService.addFavoriteBook("12345", 1L)
        );
    }

    @Test
    void removeFavoriteBook_RemovesBookFromFavorites() {
        UserProfile userProfile = UserProfileFactory.createUserProfileWithFavoriteBooks();
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.of(userProfile));

        userProfilesService.removeFavoriteBook("12345", 1L);

        verify(userProfileRepository, times(1)).save(userProfile);
    }

    @Test
    void removeFavoriteBook_ThrowsNotFoundException_WhenUserDoNotExist() {
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                userProfilesService.removeFavoriteBook("12345", 1L)
        );
    }


    @Test
    void addBookToShelf_AddsBookToShelf() {
        UserProfile userProfile = UserProfileFactory.createUserProfile();
        BookMetadataDto bookDto = BookMetadataFactory.createBookMetadataDto();
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.of(userProfile));
        when(booksService.getBookById(1L)).thenReturn(bookDto);

        userProfilesService.addBookToShelf("12345", 1L);

        verify(userProfileRepository, times(1)).save(userProfile);
    }

    @Test
    void addBookToShelf_AddsBookToShelf_ThrowsNotFoundException_WhenUserDoNotExist() {
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                userProfilesService.addBookToShelf("12345", 1L)
        );
    }

    @Test
    void removeBookFromShelf_RemovesBookFromShelf() {
        UserProfile userProfile = UserProfileFactory.createUserProfileWithShelfBooks();
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.of(userProfile));

        userProfilesService.removeBookFromShelf("12345", 1L);

        verify(userProfileRepository, times(1)).save(userProfile);
    }

    @Test
    void removeBookFromShelf_RemovesBookFromShelf_ThrowsNotFoundException_WhenUserDoNotExist() {
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                userProfilesService.removeBookFromShelf("12345", 1L)
        );
    }

    @Test
    void getAllUserProfilesPaginated_WithNameFilter_ReturnsPaginatedProfiles() {
        Pageable pageable = PageRequest.of(0, 10);
        UserProfile userProfile = UserProfileFactory.createUserProfile();
        Page<UserProfile> userProfilesPage = new PageImpl<>(List.of(userProfile));
        when(userProfileRepository.findByNameContainingIgnoreCaseWithPagination("John", pageable)).thenReturn(userProfilesPage);

        Page<UserProfileDto> result = userProfilesService.getAllUserProfilesPaginated(true, "John", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(userProfile.getId(), result.getContent().getFirst().id());
        verify(userProfileRepository, times(1)).findByNameContainingIgnoreCaseWithPagination("John", pageable);
    }

    @Test
    void getAllUserProfilesPaginated_WithoutNameFilter_ReturnsPaginatedProfiles() {
        Pageable pageable = PageRequest.of(0, 10);
        UserProfile userProfile = UserProfileFactory.createUserProfile();
        Page<UserProfile> userProfilesPage = new PageImpl<>(List.of(userProfile));
        when(userProfileRepository.findAll(pageable)).thenReturn(userProfilesPage);

        Page<UserProfileDto> result = userProfilesService.getAllUserProfilesPaginated(false, null, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(userProfile.getId(), result.getContent().getFirst().id());
        verify(userProfileRepository, times(1)).findAll(pageable);
    }

    @Test
    void getAllUserProfilesPaginated_EmptyNameFilter_ReturnsPaginatedProfiles() {
        Pageable pageable = PageRequest.of(0, 10);
        UserProfile userProfile = UserProfileFactory.createUserProfile();
        Page<UserProfile> userProfilesPage = new PageImpl<>(List.of(userProfile));
        when(userProfileRepository.findAll(pageable)).thenReturn(userProfilesPage);

        Page<UserProfileDto> result = userProfilesService.getAllUserProfilesPaginated(false, "", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(userProfile.getId(), result.getContent().getFirst().id());
        verify(userProfileRepository, times(1)).findAll(pageable);
    }


    @Test
    void create_CreatesAndReturnsUserProfile() {
        UserProfileDto userProfileDto = UserProfileFactory.createUserProfileDto();
        UserProfile userProfile = UserProfileMapper.INSTANCE.toEntity(userProfileDto);
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfileDto result = userProfilesService.create(userProfileDto);

        assertNotNull(result);
        assertEquals(userProfileDto.id(), result.id());
        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    void getAllUserProfiles_WithNameAndIncludeBooks_ReturnsProfiles() {
        UserProfile userProfile = UserProfileFactory.createUserProfile();
        when(userProfileRepository.findByNameContainingIgnoreCase("John")).thenReturn(List.of(userProfile));

        List<UserProfileDto> result = userProfilesService.getAllUserProfiles(true, "John");

        assertEquals(1, result.size());
        assertEquals(userProfile.getId(), result.getFirst().id());
        verify(userProfileRepository, times(1)).findByNameContainingIgnoreCase("John");
    }

    @Test
    void getAllUserProfilesPaginated_WithoutIncludeBooksAndName_ReturnsPaginatedProfiles() {
        Pageable pageable = PageRequest.of(0, 10);
        UserProfile userProfile = UserProfileFactory.createUserProfile();
        Page<UserProfile> userProfilesPage = new PageImpl<>(List.of(userProfile));
        when(userProfileRepository.findAll(pageable)).thenReturn(userProfilesPage);

        Page<UserProfileDto> result = userProfilesService.getAllUserProfilesPaginated(false, null, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(userProfile.getId(), result.getContent().getFirst().id());
        verify(userProfileRepository, times(1)).findAll(pageable);
    }

    @Test
    void create_WithEmptyBooks_CreatesAndReturnsUserProfile() {
        UserProfileDto userProfileDto = new UserProfileDto(
                1L,
                List.of(),
                List.of(),
                UserProfileFactory.createGoogleUserDto(),
                "2025-05-02T23:20:13.18641",
                "2025-05-07T22:55:03.095056"
        );
        UserProfile userProfile = UserProfileMapper.INSTANCE.toEntity(userProfileDto);
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfileDto result = userProfilesService.create(userProfileDto);

        assertNotNull(result);
        assertEquals(userProfileDto.id(), result.id());
        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    void getUserProfileById_InvalidIssuer_ThrowsNotImplementedException() {
        String invalidIssuer = "https://invalid-issuer.com";
        String userId = "12345";

        NotImplementedException exception = assertThrows(NotImplementedException.class,
                () -> userProfilesService.getUserProfileById(userId, invalidIssuer, true));

        assertEquals("Invalid Issuer: " + invalidIssuer, exception.getMessage());
        verify(userProfileRepository, never()).findByGoogleUserId(anyString());
    }

    @Test
    void getRecommendedBooks_ReturnsRecommendedBooks() {
        UserProfile userProfile = UserProfileFactory.createUserProfileWithFavoriteShelfBooks();
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.of(userProfile));

        List<BookMetadataDto> allBooks = BookMetadataFactory.createBookMetadataDtoList();
        when(booksService.getAllBooks(null, null, null, null)).thenReturn(allBooks);

        List<BookMetadataDto> result = userProfilesService.getRecommendedBooks("12345", 2);

        assertEquals(2, result.size());
        verify(userProfileRepository, times(1)).findByGoogleUserId("12345");
        verify(booksService, times(1)).getAllBooks(null, null, null, null);
    }

    @Test
    void getRecommendedBooks_ThrowsNotFoundException_WhenUserDoesNotExist() {
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userProfilesService.getRecommendedBooks("12345", 2));
        verify(userProfileRepository, times(1)).findByGoogleUserId("12345");
        verify(booksService, never()).getAllBooks(any(), any(), any(), any());
    }

    @Test
    void getRecommendedBooks_ReturnsEmptyList_WhenNoBooksAvailable() {
        UserProfile userProfile = UserProfileFactory.createUserProfileWithFavoriteShelfBooks();
        when(userProfileRepository.findByGoogleUserId("12345")).thenReturn(Optional.of(userProfile));

        when(booksService.getAllBooks(null, null, null, null)).thenReturn(List.of());

        List<BookMetadataDto> result = userProfilesService.getRecommendedBooks("12345", 2);

        assertTrue(result.isEmpty());
        verify(userProfileRepository, times(1)).findByGoogleUserId("12345");
        verify(booksService, times(1)).getAllBooks(null, null, null, null);
    }
}