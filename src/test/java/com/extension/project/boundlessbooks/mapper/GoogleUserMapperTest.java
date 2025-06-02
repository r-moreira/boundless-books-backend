package com.extension.project.boundlessbooks.mapper;

import com.extension.project.boundlessbooks.model.dto.GoogleUserDto;
import com.extension.project.boundlessbooks.model.entity.GoogleUser;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GoogleUserMapperTest {

    private final GoogleUserMapper mapper = GoogleUserMapper.INSTANCE;

    @Test
    void fromOAuth2User_MapsOAuth2UserToGoogleUserCorrectly() {
        Map<String, Object> attributes = Map.of(
                "sub", "1234567890",
                "email", "user@example.com",
                "name", "John Doe"
        );
        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(
                Collections.singleton(new OAuth2UserAuthority(attributes)),
                attributes,
                "sub"
        );

        GoogleUser googleUser = mapper.fromOAuth2User(oAuth2User);

        assertEquals("1234567890", googleUser.getId());
        assertEquals("user@example.com", googleUser.getEmail());
        assertEquals("John Doe", googleUser.getName());
    }

    @Test
    void toDto_MapsEntityToDtoCorrectly() {
        LocalDateTime createdAt = LocalDateTime.parse("2025-05-02T23:20:13.18641", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime updatedAt = LocalDateTime.parse("2025-05-07T22:55:03.095056", DateTimeFormatter.ISO_DATE_TIME);

        GoogleUser googleUser = new GoogleUser();
        googleUser.setId("1234567890");
        googleUser.setEmail("user@example.com");
        googleUser.setName("John Doe");
        googleUser.setCreatedAt(createdAt);
        googleUser.setUpdatedAt(updatedAt);

        GoogleUserDto dto = mapper.toDto(googleUser);

        assertEquals(googleUser.getId(), dto.id());
        assertEquals(googleUser.getEmail(), dto.email());
        assertEquals(googleUser.getName(), dto.name());
        assertEquals(createdAt.format(DateTimeFormatter.ISO_DATE_TIME), dto.createdAt());
        assertEquals(updatedAt.format(DateTimeFormatter.ISO_DATE_TIME), dto.updatedAt());
    }

    @Test
    void toEntity_MapsDtoToEntityCorrectly() {
        String createdAt = "2025-05-02T23:20:13.18641";
        String updatedAt = "2025-05-07T22:55:03.095056";

        GoogleUserDto dto = new GoogleUserDto(
                "1234567890",
                "user@example.com",
                "John Doe",
                createdAt,
                updatedAt
        );

        GoogleUser entity = mapper.toEntity(dto);

        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.email(), entity.getEmail());
        assertEquals(dto.name(), entity.getName());
        assertEquals(LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME), entity.getCreatedAt());
        assertEquals(LocalDateTime.parse(updatedAt, DateTimeFormatter.ISO_DATE_TIME), entity.getUpdatedAt());
    }
}