package com.extension.project.boundlessbooks.mapper;

import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BooksMapper {
    BooksMapper INSTANCE = Mappers.getMapper(BooksMapper.class);

    BookMetadataDto toDto(BookMetadata bookMetadata);

    @Mapping(target = "id", ignore = true)
    BookMetadata toEntity(BookMetadataDto bookMetadataDto);

    @Mapping(target = "id", source = "id")
    BookMetadata toIdentifiedEntity(BookMetadataDto bookMetadataDto, Long id);
}