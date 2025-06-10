package com.extension.project.boundlessbooks.factory;

import com.extension.project.boundlessbooks.model.dto.GoogleUserDto;
import com.extension.project.boundlessbooks.model.dto.UserProfileDto;
import com.extension.project.boundlessbooks.model.entity.GoogleUser;
import com.extension.project.boundlessbooks.model.entity.UserProfile;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserProfileFactory {

    public static UserProfile createUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setFavoriteBooks(new ArrayList<>());
        userProfile.setShelfBooks(new ArrayList<>());
        userProfile.setGoogleUser(createGoogleUser());
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setUpdatedAt(LocalDateTime.now());
        return userProfile;
    }

    public static UserProfile createUserProfileWithFavoriteBooks() {
        UserProfile userProfile = createUserProfile();
        userProfile.setFavoriteBooks(new ArrayList<>(List.of(BookMetadataFactory.createBookMetadata())));
        return userProfile;
    }

    public static UserProfile createUserProfileWithShelfBooks() {
        UserProfile userProfile = createUserProfile();
        userProfile.setShelfBooks(new ArrayList<>(List.of(BookMetadataFactory.createBookMetadata())));
        return userProfile;
    }

    public static UserProfile createUserProfileWithFavoriteShelfBooks() {
        UserProfile userProfile = createUserProfile();
        userProfile.setFavoriteBooks(new ArrayList<>(List.of(BookMetadataFactory.createBookMetadata())));
        userProfile.setShelfBooks(new ArrayList<>(List.of(BookMetadataFactory.createBookMetadata())));
        return userProfile;
    }

    public static UserProfileDto createUserProfileDto() {
        return new UserProfileDto(
                1L,
                new ArrayList<>(List.of(BookMetadataFactory.createBookMetadataDto())),
                new ArrayList<>(List.of(BookMetadataFactory.createBookMetadataDto())),
                createGoogleUserDto(),
                "2025-05-02T23:20:13.18641",
                "2025-05-07T22:55:03.095056"
        );
    }

    public static GoogleUser createGoogleUser() {
        GoogleUser googleUser = new GoogleUser();
        googleUser.setId("12345");
        googleUser.setEmail("test@example.com");
        googleUser.setName("John Doe");
        googleUser.setCreatedAt(LocalDateTime.now());
        googleUser.setUpdatedAt(LocalDateTime.now());
        return googleUser;
    }

    public static GoogleUserDto createGoogleUserDto() {
        return new GoogleUserDto(
                "12345",
                "test@example.com",
                "John Doe",
                "2025-05-02T23:20:13.18641",
                "2025-05-07T22:55:03.095056"
        );
    }
}