package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.exception.NotFoundException;
import com.extension.project.boundlessbooks.exception.NotImplementedException;
import com.extension.project.boundlessbooks.mapper.UserProfileMapper;
import com.extension.project.boundlessbooks.model.dto.UserProfileDto;
import com.extension.project.boundlessbooks.repository.UserProfileRepository;
import com.extension.project.boundlessbooks.service.UserProfilesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfilesServiceImpl implements UserProfilesService {

    private final UserProfileRepository userProfileRepository;

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
}
