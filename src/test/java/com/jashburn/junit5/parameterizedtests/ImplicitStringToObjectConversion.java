package com.jashburn.junit5.parameterizedtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ImplicitStringToObjectConversion {

    @ParameterizedTest
    @ValueSource(strings = "42 Cats")
    void implicitFallbackArgumentConversion(Book book) {
        assertEquals("42 Cats", book.getTitle());
    }
}
