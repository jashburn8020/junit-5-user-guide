package com.jashburn.junit5.parameterizedtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class CsvFileSourceTests {

    @ParameterizedTest
    @CsvFileSource(resources = "/csv_file_resource.csv", numLinesToSkip = 1)
    void testWithCsvFileSource(String country, int numOfLetters) {
        assertNotNull(country);
        assertEquals(country.length(), numOfLetters);
    }
}
