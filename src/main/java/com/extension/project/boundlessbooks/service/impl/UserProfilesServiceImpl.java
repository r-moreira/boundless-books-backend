package com.extension.project.boundlessbooks.service.impl;

import com.extension.project.boundlessbooks.exception.NotFoundException;
import com.extension.project.boundlessbooks.mapper.UserProfileMapper;
import com.extension.project.boundlessbooks.model.dto.UserProfileDto;
import com.extension.project.boundlessbooks.model.entity.GoogleUser;
import com.extension.project.boundlessbooks.repository.GoogleUserRepository;
import com.extension.project.boundlessbooks.repository.UserProfileRepository;
import com.extension.project.boundlessbooks.service.UserProfilesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfilesServiceImpl implements UserProfilesService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfileDto getUserProfileById(String id) {
        log.info("Fetching user profile by id: {}", id);

        UserProfileDto userProfileDto = userProfileRepository.findById(id)
                .map(UserProfileMapper.INSTANCE::toDto)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        log.info("User profile: {}", userProfileDto);

        return userProfileDto;

    }
}
