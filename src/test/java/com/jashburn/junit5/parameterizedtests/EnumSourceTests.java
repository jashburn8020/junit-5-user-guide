package com.jashburn.junit5.parameterizedtests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import com.jashburn.junit5.testinterfaces.Rank;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

/**
 * Run using:
 * <code>java -jar /path/to//junit-platform-console-standalone-1.6.2.jar --class-path='target/test-classes;target/classes' --scan-class-path --include-package='com.jashburn.junit5.parameterizedtests' --include-classname='.*'</code>
 * <p>
 * <code>junit-platform-console-standalone-1.6.2.jar</code> needs to be downloaded separately.
 * <p>
 * Output appears as follows:
 * 
 * <pre>
│  ├─ EnumSourceTests ✔
│  │  ├─ enumSourceWithValue(TemporalUnit) ✔
│  │  │  ├─ [1] Nanos ✔
│  │  │  ├─ [2] Micros ✔
│  │  │  ├─ [3] Millis ✔
│  │  │  ├─ [4] Seconds ✔
│  │  │  ├─ [5] Minutes ✔
│  │  │  ├─ [6] Hours ✔
│  │  │  ├─ [7] HalfDays ✔
│  │  │  ├─ [8] Days ✔
│  │  │  ├─ [9] Weeks ✔
│  │  │  ├─ [10] Months ✔
│  │  │  ├─ [11] Years ✔
│  │  │  ├─ [12] Decades ✔
│  │  │  ├─ [13] Centuries ✔
│  │  │  ├─ [14] Millennia ✔
│  │  │  ├─ [15] Eras ✔
│  │  │  └─ [16] Forever ✔
│  │  └─ compareRanks(Rank) ✔
│  │     ├─ [1] LANCE_CORPORAL ✔
│  │     ├─ [2] CORPORAL ✔
│  │     ├─ [3] SERGEANT ✔
│  │     └─ [4] STAFF_SERGEANT ✔
 * </pre>
 */
class EnumSourceTests {

    @ParameterizedTest()
    @EnumSource(mode = Mode.EXCLUDE, names = {"PRIVATE"})
    void compareRanks(Rank rank) {
        assertTrue(Rank.PRIVATE.compareTo(rank) < 0);
    }

    @ParameterizedTest
    @EnumSource(value = ChronoUnit.class)
    void enumSourceWithValue(TemporalUnit unit) {
        assertNotNull(unit);
    }
}
