package com.jashburn.junit5.parameterizedtests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Run using:
 * <code>java -jar /path/to//junit-platform-console-standalone-1.6.2.jar --class-path='target/test-classes;target/classes' --scan-class-path --include-package='com.jashburn.junit5.parameterizedtests' --include-classname='.*'</code>
 * <p>
 * <code>junit-platform-console-standalone-1.6.2.jar</code> needs to be downloaded separately.
 * <p>
 * Output appears as follows:
 * 
 * <pre>
│  └─ ValueSourceTests ✔
│     ├─ palindromes(String) ✔
│     │  ├─ [1] racecar ✔
│     │  ├─ [2] radar ✔
│     │  └─ [3] able was I ere I saw elba ✔
│     └─ palindromesWithNullEmptyAndBlanks(String) ✔
│        ├─ [1] null ✔
│        ├─ [2]  ✔
│        ├─ [3]   ✔
│        ├─ [4]     ✔
│        ├─ [5]   ✔
│        └─ [6]   ✔
 * </pre>
 */
class ValueSourceTests {

    @ParameterizedTest
    @ValueSource(strings = {"racecar", "radar", "able was I ere I saw elba"})
    void palindromes(String candidate) {
        assertTrue(StringUtils.isPalindrome(candidate));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void palindromesWithNullEmptyAndBlanks(String candidate) {
        assertTrue(StringUtils.isPalindrome(candidate));
    }
}
