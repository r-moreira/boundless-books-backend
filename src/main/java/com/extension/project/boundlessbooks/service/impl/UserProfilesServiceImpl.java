package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.exception.NotFoundException;
import com.extension.project.boundlessbooks.exception.NotImplementedException;
import com.extension.project.boundlessbooks.mapper.BooksMapper;
import com.extension.project.boundlessbooks.mapper.UserProfileMapper;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.dto.UserProfileDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import com.extension.project.boundlessbooks.model.entity.UserProfile;
import com.extension.project.boundlessbooks.repository.UserProfileRepository;
import com.extension.project.boundlessbooks.service.BooksService;
import com.extension.project.boundlessbooks.service.UserProfilesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfilesServiceImpl implements UserProfilesService {

    private final UserProfileRepository userProfileRepository;

    private final BooksService booksService;

    @Override
    public List<UserProfileDto> getAllUserProfiles(boolean includeBooks, String name) {
        log.info("Fetching all user profiles: includeBooks={}, name={}", includeBooks, name);

        return (name == null || name.isBlank()
                        ? userProfileRepository.findAll()
                        : userProfileRepository.findByNameContainingIgnoreCase(name)
                )
                .stream()
                .map(includeBooks
                        ? UserProfileMapper.INSTANCE::toDto
                        : UserProfileMapper.INSTANCE::toDtoWithoutBooks)
                .toList();
    }

    public Page<UserProfileDto> getAllUserProfilesPaginated(boolean includeBooks, String name, Pageable pageable) {
        log.info("Fetching paginated user profiles: includeBooks={}, name={}, pageable={}", includeBooks, name, pageable);

        Page<UserProfile> userProfilesPage = (name == null || name.isBlank()
                        ? userProfileRepository.findAll(pageable)
                        : userProfileRepository.findByNameContainingIgnoreCaseWithPagination(name, pageable)
                );

        return userProfilesPage.map(includeBooks
                ? UserProfileMapper.INSTANCE::toDto
                : UserProfileMapper.INSTANCE::toDtoWithoutBooks);
    }

    @Override
    public UserProfileDto create(UserProfileDto userProfileDto) {
        log.info("Creating user profile: {}", userProfileDto);

        UserProfile userProfile = UserProfileMapper.INSTANCE.toEntity(userProfileDto);
        UserProfile savedUserProfile = userProfileRepository.save(userProfile);

        return UserProfileMapper.INSTANCE.toDto(savedUserProfile);
    }


    @Override
    public UserProfileDto getUserProfileById(String id, String iss, boolean includeBooks) {
        log.info("Fetching user profile by id: {} - iss: {}", id, iss);

        if (!"https://accounts.google.com".equals(iss)) {
            throw new NotImplementedException("Invalid Issuer: " + iss);
        }

        return userProfileRepository.findByGoogleUserId(id)
                .map(includeBooks
                        ? UserProfileMapper.INSTANCE::toDto
                        : UserProfileMapper.INSTANCE::toDtoWithoutBooks)
                .orElseThrow(() -> new NotFoundException("User profile not found"));
    }

    @Override
    public void addFavoriteBook(String userId, Long bookId) {
        log.info("Adding favorite book with id: {} to user with id: {}", bookId, userId);

        UserProfile userProfile = userProfileRepository.findByGoogleUserId(userId)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        BookMetadataDto bookDto = booksService.getBookById(bookId);
        BookMetadata book = BooksMapper.INSTANCE.toIdentifiedEntity(bookDto, bookId);

        userProfile.getFavoriteBooks().add(book);
        userProfileRepository.save(userProfile);
    }

    @Override
    public void removeFavoriteBook(String id, Long bookId) {
        log.info("Removing favorite book with id: {} from user with id: {}", bookId, id);

        UserProfile userProfile = userProfileRepository.findByGoogleUserId(id)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        userProfile.getFavoriteBooks().removeIf(book -> book.getId().equals(bookId));
        userProfileRepository.save(userProfile);
    }

    @Override
    public void addBookToShelf(String id, Long bookId) {
        log.info("Adding book to shelf with id: {} to user with id: {}", bookId, id);

        UserProfile userProfile = userProfileRepository.findByGoogleUserId(id)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        BookMetadataDto bookDto = booksService.getBookById(bookId);
        BookMetadata book = BooksMapper.INSTANCE.toIdentifiedEntity(bookDto, bookId);

        userProfile.getShelfBooks().add(book);
        userProfileRepository.save(userProfile);
    }

    @Override
    public void removeBookFromShelf(String id, Long bookId) {
        log.info("Removing book from shelf with id: {} from user with id: {}", bookId, id);

        UserProfile userProfile = userProfileRepository.findByGoogleUserId(id)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        userProfile.getShelfBooks().removeIf(book -> book.getId().equals(bookId));

        userProfileRepository.save(userProfile);
    }
}
