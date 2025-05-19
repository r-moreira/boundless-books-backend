package com.extension.project.boundlessbooks.converter;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class StringToDateConverterTest {

    private final StringToDateConverter converter = new StringToDateConverter();

    @Test
    void convert_ValidDate_ReturnsDate() {
        String validDate = "2023/10/15";

        Date result = converter.convert(validDate);

        assertNotNull(result);
    }

    @Test
    void convert_InvalidDateFormat_ThrowsIllegalArgumentException() {
        String invalidDate = "15-10-2023";

        assertThrows(IllegalArgumentException.class, () -> converter.convert(invalidDate));
    }

    @Test
    void convert_InvalidDateValue_ThrowsIllegalArgumentException() {
        String invalidDate = "2023/02/30";

        assertThrows(IllegalArgumentException.class, () -> converter.convert(invalidDate));
    }

    @Test
    void convert_NullInput_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> converter.convert(null));
    }
}