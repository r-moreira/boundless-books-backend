package com.extension.project.boundlessbooks.mapper;

import com.extension.project.boundlessbooks.model.dto.GoogleUserDto;
import com.extension.project.boundlessbooks.model.entity.GoogleUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@Mapper
public interface GoogleUserMapper {
    GoogleUserMapper INSTANCE = Mappers.getMapper(GoogleUserMapper.class);

    @Mapping(target = "id", expression = "java(userDetails.getAttribute(\"sub\"))")
    @Mapping(target = "email", expression = "java(userDetails.getAttribute(\"email\"))")
    @Mapping(target = "name", expression = "java(userDetails.getAttribute(\"name\"))")
    GoogleUser fromOAuth2User(DefaultOAuth2User userDetails);

    GoogleUserDto toDto(GoogleUser googleUser);

    GoogleUser toEntity(GoogleUserDto googleUserDto);
}