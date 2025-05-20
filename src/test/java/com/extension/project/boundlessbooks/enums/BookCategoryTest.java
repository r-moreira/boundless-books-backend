package com.extension.project.boundlessbooks.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookCategoryTest {

    @Test
    void fromDisplayName_ValidDisplayName_ReturnsCorrectCategory() {
        BookCategory category = BookCategory.fromDisplayName("Ficção");
        assertNotNull(category);
        assertEquals(BookCategory.FICCAO, category);
    }

    @Test
    void fromDisplayName_InvalidDisplayName_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> BookCategory.fromDisplayName("Invalid Category"));
    }

    @Test
    void fromDisplayName_NullDisplayName_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> BookCategory.fromDisplayName(null));
    }

    @Test
    void getDisplayName_ReturnsCorrectDisplayName() {
        assertEquals("Ficção", BookCategory.FICCAO.getDisplayName());
    }

    @Test
    void getMainCategory_ReturnsCorrectMainCategory() {
        assertEquals("Literatura e Ficção", BookCategory.FICCAO.getMainCategory());
    }
}