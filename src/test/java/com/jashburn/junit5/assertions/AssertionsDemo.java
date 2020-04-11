package com.jashburn.junit5.assertions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AssertionsDemo {

    private final Calculator calculator = new Calculator();
    private final Person simpleJaneDoe = new Person("Jane", "Doe");

    /**
     * Standard assertions.
     * <ul>
     * <li>Optional failure message is now the last parameter</li>
     * <li>Failure messages can be a String {@link Supplier} for lazy evaluation to avoid
     * constructing complex messages unnecessarily</li>
     * </ul>
     * Note: This test will break on first assertion failure. See {@link #groupedAssertions()}.
     */
    @Test
    void standardAssertions() {
        assertEquals(2, calculator.add(1, 1));
        assertEquals(4, calculator.multiply(2, 2), "Optional failure message");
        assertTrue('a' < 'b', () -> "Assertion message as a Supplier");
    }

    /**
     * <code>assertTrue()</code> and <code>assertFalse()</code> also accept a
     * {@link BooleanSupplier} - supplier of boolean-valued results.
     */
    @Test
    void assertTrueFalseBooleanSupplier() {
        int number = 10;

        // Simple lambda expression
        assertTrue(() -> number % 2 == 0, number + " is not an even number.");

        // A couple of functions to test divisibility by 2
        BiFunction<Integer, Integer, Boolean> divisible = (x, y) -> x % y == 0;
        Function<Integer, Boolean> multipleOf2 = (x) -> divisible.apply(x, 2);
        assertTrue(() -> multipleOf2.apply(number), () -> "2 is not factor of " + number);

        // A stream of integers along with a predicate to check existence of even numbers
        List<Integer> numbers = Arrays.asList(1, 1, 3, 1, 2);
        assertTrue(() -> numbers.stream().distinct().anyMatch(num -> num % 2 == 0),
                "Did not find an even number in the list");
    }

    /**
     * Grouped assertions - <code>assertAll()</code> - to assert a state that requires multiple
     * assertions.
     * <ul>
     * <li>invokes all assertions given as method parameters, and reports all assertion failures
     * after all assertions have been run</li>
     * <li>optional heading to identify the asserted state</li>
     * </ul>
     */
    @Test
    void groupedAssertions() {
        Person person = new Person("Jane", "Doe", Person.Gender.F, LocalDate.of(2001, 1, 1));

        assertAll("Check a person's attributes", () -> assertEquals("John", person.getFirstName()),
                () -> assertEquals("Doe", person.getLastName()),
                () -> assertSame(Person.Gender.M, person.getGender()),
                () -> assertEquals(LocalDate.of(2001, 1, 1), person.getDateOfBirth()));
    }

    /**
     * Combining dependent and independent assertions using grouped assertions
     * ({@link Assertions#assertAll(String, org.junit.jupiter.api.function.Executable...)}).
     */
    @Test
    void dependentAssertions() {
        // Within a code block, if an assertion fails the subsequent code in the same block will be
        // skipped.
        assertAll("properties", () -> {
            assertNotNull(simpleJaneDoe.getFirstName());

            // Executed only if the previous assertion is valid.
            assertAll("first name", () -> assertTrue(simpleJaneDoe.getFirstName().startsWith("J")),
                    () -> assertTrue(simpleJaneDoe.getFirstName().endsWith("e")));
        }, () -> {
            // Grouped assertion, so processed independently of results of first name assertions.
            assertNotNull(simpleJaneDoe.getLastName());

            // Executed only if the previous assertion is valid.
            assertAll("last name", () -> assertTrue(simpleJaneDoe.getLastName().startsWith("D")),
                    () -> assertTrue(simpleJaneDoe.getLastName().endsWith("e")));
        });
    }

    /**
     * Assert that an <code>Exception</code> is thrown. Ignore the returned <code>Exception</code>
     * if additional checks are not not needed.
     * <p>
     * Uses {@link Assertions#assertThrows(Class, org.junit.jupiter.api.function.Executable)}.
     */
    @Test
    void exceptionTesting() {
        Exception exception =
                assertThrows(ArithmeticException.class, () -> calculator.divide(1, 0));
        assertEquals("/ by zero", exception.getMessage());
    }
}
