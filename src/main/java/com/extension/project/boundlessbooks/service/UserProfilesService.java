package com.extension.project.boundlessbooks.service;

import com.extension.project.boundlessbooks.model.dto.UserProfileDto;

public interface UserProfilesService {

    UserProfileDto create(UserProfileDto userProfileDto);

    UserProfileDto getUserProfileById(String id, String iss);

    void addFavoriteBook(String id, Long bookId);

    void addBookToShelf(String id, Long bookId);
}
