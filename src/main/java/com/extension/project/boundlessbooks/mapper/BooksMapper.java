package com.extension.project.boundlessbooks.mapper;

import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BooksMapper {
    BooksMapper INSTANCE = Mappers.getMapper(BooksMapper.class);

    @Mapping(target = "category", expression = "java(BookCategory.fromDisplayName(bookMetadata.getCategory()))")
    BookMetadataDto toDto(BookMetadata bookMetadata);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", expression = "java(bookMetadataDto.category().getDisplayName())")
    BookMetadata toEntity(BookMetadataDto bookMetadataDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "category", expression = "java(bookMetadataDto.category().getDisplayName())")
    BookMetadata toIdentifiedEntity(BookMetadataDto bookMetadataDto, Long id);
}