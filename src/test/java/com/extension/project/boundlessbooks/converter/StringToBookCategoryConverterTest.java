package com.extension.project.boundlessbooks.converter;

import com.extension.project.boundlessbooks.enums.BookCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringToBookCategoryConverterTest {

    private final StringToBookCategoryConverter converter = new StringToBookCategoryConverter();

    @Test
    void convert_ValidCategory_ReturnsBookCategory() {
        String validCategory = "Ficção";

        BookCategory result = converter.convert(validCategory);

        assertNotNull(result);
        assertEquals(BookCategory.FICCAO, result);
    }

    @Test
    void convert_InvalidCategory_ThrowsIllegalArgumentException() {
        String invalidCategory = "InvalidCategory";

        assertThrows(IllegalArgumentException.class, () -> converter.convert(invalidCategory));
    }

    @Test
    void convert_NullInput_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> converter.convert(null));
    }
}