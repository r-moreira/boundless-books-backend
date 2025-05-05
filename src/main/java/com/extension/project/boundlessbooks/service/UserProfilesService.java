package com.extension.project.boundlessbooks.service;

import com.extension.project.boundlessbooks.model.dto.UserProfileDto;

import java.util.List;

public interface UserProfilesService {

    List<UserProfileDto> getAllUserProfiles(boolean includeBooks, String name);

    UserProfileDto create(UserProfileDto userProfileDto);

    UserProfileDto getUserProfileById(String id, String iss, boolean includeBooks);

    void addFavoriteBook(String id, Long bookId);

    void removeFavoriteBook(String id, Long bookId);

    void addBookToShelf(String id, Long bookId);

    void removeBookFromShelf(String id, Long bookId);

}
