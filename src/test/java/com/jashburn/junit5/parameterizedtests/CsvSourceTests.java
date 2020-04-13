package com.jashburn.junit5.parameterizedtests;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CsvSourceTests {

    @ParameterizedTest
    @CsvSource({"apple, 1", "banana, 2", "'lemon, lime', 0xF1"})
    void csvSourceDefault(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"apple | 1", "| 2", "lemon, lime | 1"})
    void csvSourceDelimiter(String fruit, int rank) {
        assumeTrue(fruit != null);
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }

    @ParameterizedTest
    @CsvSource(nullValues = {"NIL", "N/A"}, emptyValue = "-", value = {"NIL, 1", "N/A, 2", "'', 3"})
    void csvSourceNullOrEmpty(String fruit, int rank) {
        assertTrue(fruit == null || fruit.equals("-"));
        assertNotEquals(0, rank);
    }
}
