package com.jashburn.junit5.displayname;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 * Run using:
 * <code>java -jar /path/to/junit-platform-console-standalone-1.6.2.jar --class-path target/test-classes --scan-class-path --include-classname='.*'</code>
 * <p>
 * <code>junit-platform-console-standalone-1.6.2.jar</code> needs to be downloaded separately.
 * <p>
 * Output appears as follows:
 * 
 * <pre>
 * │  ├─ Custom display name generator ✔
 * │  │  └─ Camel case name ✔
 * </pre>
 */
@DisplayNameGeneration(CustomDisplayNameGenerator.IndicativeSentences.class)
class CustomDisplayNameGenerator {

    @Test
    void camelCaseName() {
    }

    static class IndicativeSentences extends DisplayNameGenerator.Standard {

        @Override
        public String generateDisplayNameForClass(Class<?> testClass) {
            return replaceCamelCase(super.generateDisplayNameForClass(testClass));
        }

        @Override
        public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
            return replaceCamelCase(super.generateDisplayNameForNestedClass(nestedClass));
        }

        @Override
        public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
            return replaceCamelCase(testMethod.getName());
        }

        String replaceCamelCase(String camelCase) {
            String replacedStr = Arrays.stream(camelCase.split("(?<!^)(?=[A-Z])"))
                    .map(word -> word.toLowerCase()).collect(Collectors.joining(" "));

            return replacedStr.substring(0, 1).toUpperCase() + replacedStr.substring(1);
        }
    }
}
