package com.jashburn.junit5.parameterizedtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

class ArgumentsSourceTests {

    @ParameterizedTest
    @ArgumentsSource(SingleParamArgumentsProvider.class)
    void argumentsSourceOneParam(String argument) {
        assertNotNull(argument);
    }

    static class SingleParamArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of("apple", "banana").map(Arguments::of);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(MultiParamsArgumentsProvider.class)
    void argumentsSourceMultiParams(String fiveLetterStr, int oneOrTwo, List<String> sizeTwoList) {
        assertEquals(5, fiveLetterStr.length());
        assertTrue(oneOrTwo >= 1 && oneOrTwo <= 2);
        assertEquals(2, sizeTwoList.size());
    }

    static class MultiParamsArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(arguments("apple", 1, Arrays.asList("a", "b")),
                    arguments("lemon", 2, Arrays.asList("x", "y")));
        }
    }
}
