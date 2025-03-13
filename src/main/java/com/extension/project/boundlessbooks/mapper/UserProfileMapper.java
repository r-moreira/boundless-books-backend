package com.extension.project.boundlessbooks.mapper;

import com.extension.project.boundlessbooks.model.dto.UserProfileDto;
import com.extension.project.boundlessbooks.model.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {GoogleUserMapper.class, BooksMapper.class})
public interface UserProfileMapper {
    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    @Mapping(source = "googleUser", target = "googleUser")
    UserProfileDto toDto(UserProfile userProfile);

    UserProfile toEntity(UserProfileDto userProfileDto);
}

