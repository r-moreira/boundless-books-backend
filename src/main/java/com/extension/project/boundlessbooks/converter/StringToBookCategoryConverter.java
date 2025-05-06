package com.extension.project.boundlessbooks.converter;

import com.extension.project.boundlessbooks.enums.BookCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StringToBookCategoryConverter implements Converter<String, BookCategory> {

    @Override
    public BookCategory convert(@NonNull String source) {
        return BookCategory.fromDisplayName(source);
    }
}