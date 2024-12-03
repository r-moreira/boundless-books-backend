package com.extension.project.boundlessbooks.mapper;

import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface BooksMapper {
    BooksMapper INSTANCE = Mappers.getMapper(BooksMapper.class);

//    @Mapping(target = "releaseDate", qualifiedByName = "dateToString")
    BookMetadataDto toDto(BookMetadata bookMetadata);

//    @Mapping(target = "releaseDate", qualifiedByName = "stringToDate")
    @Mapping(target = "id", ignore = true)
    BookMetadata toEntity(BookMetadataDto bookMetadataDto);

    @Mapping(target = "id", source = "id")
    BookMetadata toIdentifiedEntity(BookMetadataDto bookMetadataDto, Long id);

    @Named("stringToReleaseDate")
    static Date stringToDate(String date) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Named("releaseDateToString")
    static String dateToString(Date date) {
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }
}