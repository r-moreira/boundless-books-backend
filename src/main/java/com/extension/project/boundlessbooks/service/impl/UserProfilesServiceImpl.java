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
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfilesServiceImpl implements UserProfilesService {

    private final UserProfileRepository userProfileRepository;

    private final BooksService booksService;


    @Override
    public UserProfileDto getUserProfileById(String id, String iss) {
        log.info("Fetching user profile by id: {} - iss: {}", id, iss);

        if (!"https://accounts.google.com".equals(iss)) {
            throw new NotImplementedException("Invalid Issuer: " + iss);
        }

        return userProfileRepository.findByGoogleUserId(id)
                .map(UserProfileMapper.INSTANCE::toDto)
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
    public void addBookToShelf(String id, Long bookId) {
        log.info("Adding book to shelf with id: {} to user with id: {}", bookId, id);

        UserProfile userProfile = userProfileRepository.findByGoogleUserId(id)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        BookMetadataDto bookDto = booksService.getBookById(bookId);
        BookMetadata book = BooksMapper.INSTANCE.toIdentifiedEntity(bookDto, bookId);

        userProfile.getShelfBooks().add(book);
        userProfileRepository.save(userProfile);
    }
}
