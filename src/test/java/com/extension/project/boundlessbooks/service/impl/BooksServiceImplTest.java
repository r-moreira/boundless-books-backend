package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.enums.BookCategory;
import com.extension.project.boundlessbooks.exception.NotFoundException;
import com.extension.project.boundlessbooks.factory.BookMetadataFactory;
import com.extension.project.boundlessbooks.factory.UserProfileFactory;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import com.extension.project.boundlessbooks.model.entity.UserProfile;
import com.extension.project.boundlessbooks.repository.BooksRepository;
import com.extension.project.boundlessbooks.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BooksServiceImplTest {

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private BooksServiceImpl booksService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllBooks_ReturnsBooks() {
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        when(booksRepository.findBooksByFilters(null, null, null, null))
                .thenReturn(List.of(book));

        List<BookMetadataDto> result = booksService.getAllBooks(null, null, null, null);

        assertEquals(1, result.size());
        verify(booksRepository, times(1)).findBooksByFilters(null, null, null, null);
    }

    @Test
    void getBookById_ExistingBook_ReturnsBook() {
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));

        BookMetadataDto result = booksService.getBookById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(booksRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_NonExistingBook_ThrowsNotFoundException() {
        when(booksRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> booksService.getBookById(1L));
        verify(booksRepository, times(1)).findById(1L);
    }

    @Test
    void createBook_SavesBook() {
        BookMetadataDto bookDto = BookMetadataFactory.createBookMetadataDto();
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        when(booksRepository.save(any(BookMetadata.class))).thenReturn(book);

        BookMetadataDto result = booksService.createBook(bookDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(booksRepository, times(1)).save(any(BookMetadata.class));
    }

    @Test
    void deleteBook_ExistingBook_DeletesBook() {
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));

        booksService.deleteBook(1L);

        verify(booksRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_NonExistingBook_ThrowsNotFoundException() {
        when(booksRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> booksService.deleteBook(1L));
        verify(booksRepository, times(1)).findById(1L);
    }

    @Test
    void getAllBooksPaginated_ReturnsPaginatedBooks() {
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        Page<BookMetadata> page = new PageImpl<>(List.of(book));
        when(booksRepository.findBooksByFiltersWithPagination(null, null, null, null, PageRequest.of(0, 10)))
                .thenReturn(page);

        Page<BookMetadataDto> result = booksService.getAllBooksPaginated(null, null, null, null, PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        verify(booksRepository, times(1)).findBooksByFiltersWithPagination(null, null, null, null, PageRequest.of(0, 10));
    }

    @Test
    void getAllBooks_WithTitleFilter_ReturnsFilteredBooks() {
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        when(booksRepository.findBooksByFilters("Harry Potter", null, null, null))
                .thenReturn(List.of(book));

        List<BookMetadataDto> result = booksService.getAllBooks("Harry Potter", null, null, null);

        assertEquals(1, result.size());
        assertEquals("Harry Potter e a Ordem da Fênix", result.getFirst().title());
        verify(booksRepository, times(1)).findBooksByFilters("Harry Potter", null, null, null);
    }

    @Test
    void getAllBooks_WithAuthorFilter_ReturnsFilteredBooks() {
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        when(booksRepository.findBooksByFilters(null, "J.K. Rowling", null, null))
                .thenReturn(List.of(book));

        List<BookMetadataDto> result = booksService.getAllBooks(null, "J.K. Rowling", null, null);

        assertEquals(1, result.size());
        assertEquals("J.K. Rowling", result.getFirst().author());
        verify(booksRepository, times(1)).findBooksByFilters(null, "J.K. Rowling", null, null);
    }

    @Test
    void getAllBooks_WithCategoryFilter_ReturnsFilteredBooks() {
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        when(booksRepository.findBooksByFilters(null, null, "Fantasia", null))
                .thenReturn(List.of(book));

        List<BookMetadataDto> result = booksService.getAllBooks(null, null, BookCategory.FANTASIA, null);

        assertEquals(1, result.size());
        assertEquals(BookCategory.FANTASIA, result.getFirst().category());
        verify(booksRepository, times(1)).findBooksByFilters(null, null, "Fantasia", null);
    }

    @Test
    void getAllBooks_WithReleaseDateFilter_ReturnsFilteredBooks() {
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        Date releaseDate = book.getReleaseDate();
        when(booksRepository.findBooksByFilters(null, null, null, releaseDate))
                .thenReturn(List.of(book));

        List<BookMetadataDto> result = booksService.getAllBooks(null, null, null, releaseDate);

        assertEquals(1, result.size());
        assertEquals(releaseDate, result.getFirst().releaseDate());
        verify(booksRepository, times(1)).findBooksByFilters(null, null, null, releaseDate);
    }

    @Test
    void getAllBooksPaginated_WithFilters_ReturnsFilteredPaginatedBooks() {
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        Page<BookMetadata> page = new PageImpl<>(List.of(book));
        when(booksRepository.findBooksByFiltersWithPagination("Harry Potter", "J.K. Rowling", "Fantasia", book.getReleaseDate(), PageRequest.of(0, 10)))
                .thenReturn(page);

        Page<BookMetadataDto> result = booksService.getAllBooksPaginated("Harry Potter", "J.K. Rowling", BookCategory.FANTASIA, book.getReleaseDate(), PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("Harry Potter e a Ordem da Fênix", result.getContent().getFirst().title());
        verify(booksRepository, times(1)).findBooksByFiltersWithPagination("Harry Potter", "J.K. Rowling", "Fantasia", book.getReleaseDate(), PageRequest.of(0, 10));
    }

    @Test
    void createBooks_SavesBooks() {
        List<BookMetadataDto> bookDtos = List.of(BookMetadataFactory.createBookMetadataDto());
        List<BookMetadata> books = List.of(BookMetadataFactory.createBookMetadata());
        when(booksRepository.saveAll(anyList())).thenReturn(books);

        List<BookMetadataDto> result = booksService.createBooks(bookDtos);

        assertEquals(1, result.size());
        assertEquals(bookDtos.get(0).title(), result.get(0).title());
        verify(booksRepository, times(1)).saveAll(anyList());
    }

    @Test
    void updateBook_ExistingBook_UpdatesBook() {
        BookMetadataDto bookDto = BookMetadataFactory.createBookMetadataDto();
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));
        when(booksRepository.save(any(BookMetadata.class))).thenReturn(book);

        BookMetadataDto result = booksService.updateBook(1L, bookDto);

        assertNotNull(result);
        assertEquals(bookDto.title(), result.title());
        verify(booksRepository, times(1)).findById(1L);
        verify(booksRepository, times(1)).save(any(BookMetadata.class));
    }

    @Test
    void updateBook_NonExistingBook_ThrowsNotFoundException() {
        BookMetadataDto bookDto = BookMetadataFactory.createBookMetadataDto();
        when(booksRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> booksService.updateBook(1L, bookDto));
        verify(booksRepository, times(1)).findById(1L);
        verify(booksRepository, never()).save(any(BookMetadata.class));
    }

    @Test
    void deleteAllBooks_RemovesAllBooksAndUpdatesUsers() {
        BookMetadata book = BookMetadataFactory.createBookMetadata();
        UserProfile user = UserProfileFactory.createUserProfileWithFavoriteBooks();
        book.setFavoriteByUsers(new ArrayList<>(List.of(user)));
        book.setShelfByUsers(new ArrayList<>(List.of(user)));
        when(booksRepository.findAll()).thenReturn(List.of(book));

        booksService.deleteAllBooks();

        verify(userProfileRepository, times(2)).save(user);
        verify(booksRepository, times(1)).deleteAll();
    }


}