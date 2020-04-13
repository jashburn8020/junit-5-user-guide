package com.jashburn.junit5.parameterizedtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MethodSourceTests {

    /**
     * Single parameter, stream of instances of the parameter type.
     */
    @ParameterizedTest
    @MethodSource("stringProviderStream")
    void explicitLocalMethodSourceStream(String argument) {
        assertNotNull(argument);
    }

    private static Stream<String> stringProviderStream() {
        return Stream.of("apple", "banana");
    }

    /**
     * Single parameter, array of instances of the parameter type.
     */
    @ParameterizedTest
    @MethodSource("stringProviderArray")
    void explicitLocalMethodSourceArray(String argument) {
        assertNotNull(argument);
    }

    private static String[] stringProviderArray() {
        return new String[] {"apple", "banana"};
    }

    /**
     * Factory method name not provided.
     */
    @ParameterizedTest
    @MethodSource
    void testWithDefaultLocalMethodSource(String argument) {
        assertNotNull(argument);
    }

    private static Stream<String> testWithDefaultLocalMethodSource() {
        return Stream.of("apple", "banana");
    }

    /**
     * Stream of primitive types.
     */
    @ParameterizedTest
    @MethodSource("oddRange")
    void testOddIntegers(int number) {
        assertTrue(number % 2 == 1);
    }

    private static IntStream oddRange() {
        return IntStream.range(1, 10).filter(num -> (num & 1) == 1);
    }

    /**
     * Multiple parameters, factory method returns stream of Arguments.
     */
    @ParameterizedTest
    @MethodSource("stringIntAndListArgumentsStream")
    void multiArgArgumentsStream(String fiveLetterStr, int oneOrTwo, List<String> sizeTwoList) {
        assertEquals(5, fiveLetterStr.length());
        assertTrue(oneOrTwo >= 1 && oneOrTwo <= 2);
        assertEquals(2, sizeTwoList.size());
    }

    private static Stream<Arguments> stringIntAndListArgumentsStream() {
        return Stream.of(arguments("apple", 1, Arrays.asList("a", "b")),
                arguments("lemon", 2, Arrays.asList("x", "y")));
    }

    /**
     * Multiple parameters, factory method returns array of arrays.
     */
    @ParameterizedTest
    @MethodSource("stringIntAndListArray")
    void multiArgArray(String fiveLetterStr, int oneOrTwo, List<String> sizeTwoList) {
        assertEquals(5, fiveLetterStr.length());
        assertTrue(oneOrTwo >= 1 && oneOrTwo <= 2);
        assertEquals(2, sizeTwoList.size());
    }

    private static Object[][] stringIntAndListArray() {
        return new Object[][] {{"apple", 1, Arrays.asList("a", "b")},
                {"lemon", 2, Arrays.asList("x", "y")}};
    }
}
