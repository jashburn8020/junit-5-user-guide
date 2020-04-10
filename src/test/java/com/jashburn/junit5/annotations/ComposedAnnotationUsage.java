package com.jashburn.junit5.annotations;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComposedAnnotationUsage {

    @FastTest
    void testComposedAnnotation() {
        int number = 10;
        assertTrue(number % 2 == 0);
    }
}
