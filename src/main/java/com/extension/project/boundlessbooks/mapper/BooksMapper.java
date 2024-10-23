package com.extension.project.boundlessbooks.mapper;

import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BooksMapper {
    BooksMapper INSTANCE = Mappers.getMapper(BooksMapper.class);

    BookMetadataDto toDto(BookMetadata bookMetadata);

    BookMetadata toEntity(BookMetadataDto bookMetadataDto);
}