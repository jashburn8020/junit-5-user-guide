package com.jashburn.junit5.parameterizedtests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Run using:
 * <code>java -jar /path/to//junit-platform-console-standalone-1.6.2.jar --class-path='target/test-classes;target/classes' --scan-class-path --include-package='com.jashburn.junit5.parameterizedtests' --include-classname='.*'</code>
 * <p>
 * <code>junit-platform-console-standalone-1.6.2.jar</code> needs to be downloaded separately.
 * <p>
 * Output appears as follows:
 * 
 * <pre>
│  ├─ CustomDisplayNames ✔
│  │  └─ Display name of container ✔
│  │     ├─ 1 => 'apple' is ranked 1 (apple, 1) ✔
│  │     ├─ 2 => 'banana' is ranked 2 (banana, 2) ✔
│  │     └─ 3 => 'lemon, lime' is ranked 3 (lemon, lime, 3) ✔
 * </pre>
 */
class CustomDisplayNames {

    @DisplayName("Display name of container")
    @ParameterizedTest(name = "{index} => ''{0}'' is ranked {1} ({arguments})")
    @CsvSource({"apple, 1", "banana, 2", "'lemon, lime', 3"})
    void testWithCustomDisplayNames(String fruit, int rank) {
    }
}
