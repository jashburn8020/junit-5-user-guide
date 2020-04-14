package com.jashburn.junit5.parameterizedtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.EnumSource;

public class ExplicitConversion {

    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void explicitArgumentConversion(@ConvertWith(ToStringArgumentConverter.class) String argument) {

        assertNotNull(ChronoUnit.valueOf(argument));
    }

    static class ToStringArgumentConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) {
            assertEquals(String.class, targetType, "Can only convert to String");
            if (source instanceof Enum<?>) {
                return ((Enum<?>) source).name();
            }
            return String.valueOf(source);
        }
    }
}
