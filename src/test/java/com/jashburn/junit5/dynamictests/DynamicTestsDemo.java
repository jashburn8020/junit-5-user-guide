package com.jashburn.junit5.dynamictests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import com.jashburn.junit5.parameterizedtests.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.function.ThrowingConsumer;

/**
 * Note: disable format-on-save ("editor.formatOnSave": false) or save without formatting (Ctrl-k s)
 * <p>
 * Run using:
 * <code>java -jar /path/to/junit-platform-console-standalone-1.6.2.jar --class-path='target/test-classes;target/classes' --scan-class-path --include-package='com.jashburn.junit5.dynamictests' --include-classname='.*'</code>
 * <p>
 * Output:
 * 
 * <pre>
 │  └─ DynamicTestsDemo ✔
│     ├─ nestedDynamicContainers() ✔
│        │  2020-04-14T19:19:00.129168
│        │     State = `BeforeEach`
│        │     Test = `nestedDynamicContainers()`
│        │  2020-04-14T19:19:00.152568
│        │     State = `AfterEach`
│        │     Test = `nestedDynamicContainers()`
│     │  ├─ Container a ✔
│     │  │  ├─ not null ✔
│     │  │  └─ Properties ✔
│     │  │     ├─ length == 1 ✔
│     │  │     └─ is lowercase ✔
│     │  ├─ Container b ✔
│     │  │  ├─ not null ✔
│     │  │  └─ Properties ✔
│     │  │     ├─ length == 1 ✔
│     │  │     └─ is lowercase ✔
│     │  └─ Container c ✔
│     │     ├─ not null ✔
│     │     └─ Properties ✔
│     │        ├─ length == 1 ✔
│     │        └─ is lowercase ✔
│     ├─ singleDynamicContainer() ✔
│        │  2020-04-14T19:19:00.154601
│        │     State = `BeforeEach`
│        │     Test = `singleDynamicContainer()`
│        │  2020-04-14T19:19:00.157861
│        │     State = `AfterEach`
│        │     Test = `singleDynamicContainer()`
│     │  └─ palindromes container ✔
│     │     ├─ racecar ✔
│     │     ├─ radar ✔
│     │     ├─ mom ✔
│     │     └─ dad ✔
│     ├─ dynamicNumberOfTests() ✔
│        │  2020-04-14T19:19:00.158787
│        │     State = `BeforeEach`
│        │     Test = `dynamicNumberOfTests()`
│        │  2020-04-14T19:19:00.161899
│        │     State = `AfterEach`
│        │     Test = `dynamicNumberOfTests()`
│     │  ├─ input: 0 ✔
│     │  ├─ input: 2 ✔
│     │  ├─ input: 4 ✔
│     │  ├─ input: 6 ✔
│     │  └─ input: 8 ✔
│     ├─ iterableDynamicTests() ✔
│        │  2020-04-14T19:19:00.162679
│        │     State = `BeforeEach`
│        │     Test = `iterableDynamicTests()`
│        │  2020-04-14T19:19:00.164355
│        │     State = `AfterEach`
│        │     Test = `iterableDynamicTests()`
│     │  ├─ mom ✔
│     │  └─ dad ✔
│     ├─ intStreamOfDynamicTests() ✔
│        │  2020-04-14T19:19:00.165163
│        │     State = `BeforeEach`
│        │     Test = `intStreamOfDynamicTests()`
│        │  2020-04-14T19:19:00.168509
│        │     State = `AfterEach`
│        │     Test = `intStreamOfDynamicTests()`
│     │  ├─ test with 1 ✔
│     │  ├─ test with 3 ✔
│     │  ├─ test with 5 ✔
│     │  ├─ test with 7 ✔
│     │  └─ test with 9 ✔
│     ├─ singleDynamicNode() ✔
│        │  2020-04-14T19:19:00.169260
│        │     State = `BeforeEach`
│        │     Test = `singleDynamicNode()`
│        │  2020-04-14T19:19:00.170395
│        │     State = `AfterEach`
│        │     Test = `singleDynamicNode()`
│     │  └─ pop is a palindrome ✔
│     └─ streamOfDynamicTests() ✔
│        │  2020-04-14T19:19:00.171152
│        │     State = `BeforeEach`
│        │     Test = `streamOfDynamicTests()`
│        │  2020-04-14T19:19:00.172512
│        │     State = `AfterEach`
│        │     Test = `streamOfDynamicTests()`
│        ├─ mom ✔
│        └─ dad ✔
 * </pre>
 */
class DynamicTestsDemo {

    @BeforeEach
    void setUp(TestReporter reporter, TestInfo info) {
        reporter.publishEntry(Map.of("State", "BeforeEach", "Test", info.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestReporter reporter, TestInfo info) {
        reporter.publishEntry(Map.of("State", "AfterEach", "Test", info.getDisplayName()));
    }

    /**
     * @return a single <code>DynamicNode</code>
     */
    @TestFactory
    DynamicNode singleDynamicNode() {
        String candidate = "pop";
        return dynamicTest(candidate + " is a palindrome",
            () -> assertTrue(StringUtils.isPalindrome(candidate)));
    }

    /**
     * @return an <code>Iterable</code> of <code>DynamicTest</code>s
     */
    @TestFactory
    Iterable<DynamicNode> iterableDynamicTests() {
        return Arrays.asList(
            dynamicTest("mom", () -> assertTrue(StringUtils.isPalindrome("mom"))),
            dynamicTest("dad", () -> assertTrue(StringUtils.isPalindrome("dad")))
        );
    }

    /**
     * @return a stream of <code>DynamicTest</code>s
     */
    @TestFactory
    Stream<DynamicNode> streamOfDynamicTests() {
        return Stream.of("mom", "dad").map(candidate ->
            dynamicTest(candidate, () -> assertTrue(StringUtils.isPalindrome(candidate)))
        );
    }

    /**
     * @return a stream of <code>DynamicNode</code>s
     */
    @TestFactory
    Stream<DynamicNode> intStreamOfDynamicTests() {
        return IntStream.iterate(1, n -> n < 10, n -> n + 2).mapToObj(num ->
            dynamicTest("test with " + num, () -> assertTrue((num & 1) == 1))
        );
    }

    /**
     * @return a <code>DynamicContainer</code> containing a stream of <code>DynamicTest</code>s
     */
    @TestFactory
    DynamicNode singleDynamicContainer() {
        return dynamicContainer("palindromes container",
            Stream.of("racecar", "radar", "mom", "dad").map(candidate ->
                dynamicTest(candidate, () -> assertTrue(StringUtils.isPalindrome(candidate)))
            )
        );
    }

    /**
     * @return a stream of <code>DynamicContainer</code>s containing nested
     *         <code>DynamicTest</code>s and <code>DynamicContainer</code>s
     */
    @TestFactory
    Stream<DynamicNode> nestedDynamicContainers() {
        return Stream.of("a", "b", "c").map(input -> 
            dynamicContainer("Container " + input, Stream.of(
                dynamicTest("not null", () -> assertNotNull(input)),
                dynamicContainer("Properties", Stream.of(
                    dynamicTest("length == 1", () -> assertEquals(1, input.length())),
                    dynamicTest("is lowercase",
                        () -> assertEquals(input.toLowerCase(), input)
                    )
                ))
            ))
        );
    }

    /**
     * Use this method when the set of dynamic tests is nondeterministic in nature. The supplied
     * inputGenerator is responsible for generating input values. A DynamicTest will be added to the
     * resulting stream for each dynamically generated input value, using the supplied
     * displayNameGenerator and testExecutor
     */
    @TestFactory
    Stream<? extends DynamicNode> dynamicNumberOfTests() {
        Iterator<Integer> evenNumsGenerator = EvenNumbersGenerator.generator();
        Function<Integer, String> displayNameGenerator = input -> "input: " + input;
        ThrowingConsumer<Integer> testExecutor = input -> assertTrue((input & 1) == 0);

        return DynamicTest.stream(evenNumsGenerator, displayNameGenerator, testExecutor);
    }
}
