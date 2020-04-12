package com.jashburn.junit5.dependencyinjection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.Map;
import com.jashburn.junit5.dependencyinjection.RandomParametersExtension.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RandomParametersExtension.class)
class RandomParametersTest {

    @Test
    void injectsInteger(TestReporter reporter, @Random int i, @Random int j) {
        reporter.publishEntry(Map.of("i", Integer.toString(i), "j", Integer.toString(j)));
        assertNotEquals(i, j);
    }

    @Test
    void injectsDouble(TestReporter reporter, @Random double d) {
        reporter.publishEntry("d", Double.toString(d));
        assertEquals(0.0, d, 1.0);
    }
}
