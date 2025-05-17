package com.extension.project.boundlessbooks.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StringToDateConverter implements Converter<String, Date> {

    private static final String DATE_PATTERN = "yyyy/MM/dd";

    @Override
    public Date convert(@NonNull String source) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
            dateFormat.setLenient(false);
            return dateFormat.parse(source);
        } catch (ParseException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: " + DATE_PATTERN);
        }
    }
}