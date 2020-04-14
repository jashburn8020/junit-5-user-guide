package com.jashburn.junit5.parameterizedtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import com.jashburn.junit5.assertions.Person;
import com.jashburn.junit5.assertions.Person.Gender;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;

class ArgumentAggregation {

    @ParameterizedTest
    @CsvSource({"Jane, Doe, F, 1990-05-20", "John, Doe, M, 1990-10-22"})
    void argumentsAccessor(ArgumentsAccessor arguments) {
        Person person = new Person(arguments.getString(0), arguments.getString(1),
                arguments.get(2, Gender.class), arguments.get(3, LocalDate.class));

        if (person.getFirstName().equals("Jane")) {
            assertEquals(Gender.F, person.getGender());
        } else {
            assertEquals(Gender.M, person.getGender());
        }
        assertEquals("Doe", person.getLastName());
        assertEquals(1990, person.getDateOfBirth().getYear());
    }

    @ParameterizedTest
    @CsvSource({"Jane, Doe, F, 1990-05-20", "John, Doe, M, 1990-10-22"})
    void customArgumentsAggregator(@AggregateWith(PersonAggregator.class) Person person) {
        assertEquals("Doe", person.getLastName());
    }

    static class PersonAggregator implements ArgumentsAggregator {
        @Override
        public Person aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
            return new Person(arguments.getString(0), arguments.getString(1),
                    arguments.get(2, Gender.class), arguments.get(3, LocalDate.class));
        }
    }

    @ParameterizedTest
    @CsvSource({"Jane, Doe, F, 1990-05-20", "John, Doe, M, 1990-10-22"})
    void customAggregatorAnnotation(@CsvToPerson Person person) {
        assertEquals(1990, person.getDateOfBirth().getYear());
    }
}
