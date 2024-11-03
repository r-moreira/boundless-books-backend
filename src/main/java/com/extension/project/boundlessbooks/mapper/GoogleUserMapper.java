package com.extension.project.boundlessbooks.mapper;

import com.extension.project.boundlessbooks.model.dto.GoogleUserDto;
import com.extension.project.boundlessbooks.model.entity.GoogleUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GoogleUserMapper {
    GoogleUserMapper INSTANCE = Mappers.getMapper(GoogleUserMapper.class);

    GoogleUserDto toDto(GoogleUser googleUser);

    GoogleUser toEntity(GoogleUserDto googleUserDto);
}