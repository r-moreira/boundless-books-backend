package com.extension.project.boundlessbooks.converter;

import com.extension.project.boundlessbooks.enums.InteractionType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToInteractionTypeConverter implements Converter<String, InteractionType> {

    @Override
    public InteractionType convert(String source) {
        return InteractionType.fromValue(source.toUpperCase());
    }
}
