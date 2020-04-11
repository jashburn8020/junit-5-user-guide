package com.jashburn.junit5.displayname;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 * Run using:
 * <code>java -jar /path/to/junit-platform-console-standalone-1.6.2.jar --class-path target/test-classes --scan-class-path --include-classname='.*'</code>
 * <p>
 * <code>junit-platform-console-standalone-1.6.2.jar</code> needs to be downloaded separately.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DisplayNameGeneratorReplaceUnderscores {

    @Test
    void test_spaces_ok() {
    }

    @Test
    void test_spaces_fail() {
    }
}
