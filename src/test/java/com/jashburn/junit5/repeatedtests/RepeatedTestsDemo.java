package com.jashburn.junit5.repeatedtests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

/**
 * Run using:
 * <code>java -jar /path/to/junit-platform-console-standalone-1.6.2.jar --class-path='target/test-classes' --scan-class-path --include-classname='.*RepeatedTestsDemo'</code>
 * <p>
 * <code>junit-platform-console-standalone-1.6.2.jar</code> needs to be downloaded separately.
 * <p>
 * Output appears as follows:
 * 
 * <pre>
 * │  └─ RepeatedTestsDemo ✔
 * │     └─ Repeat! ✔
 * │        ├─ Repeat! 1/3 ✔
 * │        │     2020-04-13T01:16:27.308110 customDisplayName = `about to execute repetition 1 of 3`
 * │        ├─ Repeat! 2/3 ✔
 * │        │     2020-04-13T01:16:27.319146 customDisplayName = `about to execute repetition 2 of 3`
 * │        └─ Repeat! 3/3 ✔
 * │              2020-04-13T01:16:27.321333 customDisplayName = `about to execute repetition 3 of 3`
 * </pre>
 */
class RepeatedTestsDemo {

    @BeforeEach
    void setUp(TestInfo testInfo, RepetitionInfo repetitionInfo, TestReporter reporter) {
        reporter.publishEntry(testInfo.getTestMethod().get().getName(),
                String.format("about to execute repetition %d of %d",
                        repetitionInfo.getCurrentRepetition(),
                        repetitionInfo.getTotalRepetitions()));
    }

    @DisplayName("Repeat!")
    @RepeatedTest(value = 3, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    void customDisplayName() {
    }
}
