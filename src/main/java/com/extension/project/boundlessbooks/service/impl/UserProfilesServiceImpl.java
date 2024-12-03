package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.model.entity.GoogleUser;
import com.extension.project.boundlessbooks.repository.GoogleUserRepository;
import com.extension.project.boundlessbooks.service.UserProfilesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfilesServiceImpl implements UserProfilesService {

    private final GoogleUserRepository googleUserRepository;

    @Override
    public void getUserProfileById(String id) {
        log.info("Fetching user profile by id: {}", id);

        Optional<GoogleUser> googleUser = googleUserRepository.findById(id);

        googleUser.ifPresent(user -> log.info("User profile: {}", user));
    }
}
