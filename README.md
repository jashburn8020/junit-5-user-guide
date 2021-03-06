# JUnit 5 User Guide

- [JUnit 5 User Guide](#junit-5-user-guide)
  - [Annotations](#annotations)
    - [Meta-Annotations and Composed Annotations](#meta-annotations-and-composed-annotations)
  - [Test Classes and Methods](#test-classes-and-methods)
  - [Display Names](#display-names)
    - [Display Name Generators](#display-name-generators)
  - [Assertions](#assertions)
  - [Assumptions](#assumptions)
  - [Conditional Test Execution](#conditional-test-execution)
  - [Tagging](#tagging)
  - [Test Execution Order](#test-execution-order)
  - [Test Instance Lifecycle](#test-instance-lifecycle)
  - [Nested Tests](#nested-tests)
  - [Dependency Injection for Constructors and Methods](#dependency-injection-for-constructors-and-methods)
  - [Test Interfaces and Default Methods](#test-interfaces-and-default-methods)
  - [Repeated Tests](#repeated-tests)
  - [Parameterized Tests](#parameterized-tests)
    - [Consuming Arguments](#consuming-arguments)
    - [Sources of Arguments](#sources-of-arguments)
      - [`@ValueSource`](#valuesource)
      - [Null and Empty Sources](#null-and-empty-sources)
      - [`@EnumSource`](#enumsource)
      - [`@MethodSource`](#methodsource)
      - [`@CsvSource`](#csvsource)
      - [`@CsvFileSource`](#csvfilesource)
      - [`@ArgumentsSource`](#argumentssource)
    - [Argument Conversion](#argument-conversion)
      - [Widening Conversion](#widening-conversion)
      - [Implicit Conversion](#implicit-conversion)
      - [Explicit conversion](#explicit-conversion)
    - [Argument Aggregation](#argument-aggregation)
    - [Customizing Display Names](#customizing-display-names)
  - [Dynamic Tests](#dynamic-tests)
  - [Timeouts](#timeouts)
  - [Parallel Execution](#parallel-execution)
  - [Built-in Extensions](#built-in-extensions)
    - [The `TempDirectory` Extension](#the-tempdirectory-extension)
  - [Sources](#sources)

## Annotations

- Unless otherwise stated, all core annotations are located in the `org.junit.jupiter.api` package in the `junit-jupiter-api` module
- **`@Test`**: Denotes that a method is a test method
- **`@ParameterizedTest`**: Denotes that a method is a parameterized test
- **`@RepeatedTest`**: Denotes that a method is a test template for a repeated test
- **`@TestFactory`**: Denotes that a method is a test factory for dynamic tests
- **`@TestTemplate`**: Denotes that a method is a template for test cases designed to be invoked multiple times depending on the number of invocation contexts returned by the registered providers
- **`@TestMethodOrder`**: Used to configure the test method execution order for the annotated test class; similar to JUnit 4's `@FixMethodOrder`
- **`@TestInstance`**: Used to configure the test instance lifecycle for the annotated test class
- **`@DisplayName`**: Declares a custom display name for the test class or test method
- **`@DisplayNameGeneration`**: Declares a custom display name generator for the test class
- **`@BeforeEach`**: Denotes that the annotated method should be executed before each `@Test`, `@RepeatedTest`, `@ParameterizedTest`, or `@TestFactory` method in the current class; analogous to JUnit 4's `@Before`
- **`@AfterEach`**: Denotes that the annotated method should be executed after each `@Test`, `@RepeatedTest`, `@ParameterizedTest`, or `@TestFactory` method in the current class; analogous to JUnit 4's `@After`
- **`@BeforeAll`**: Denotes that the annotated method should be executed before all `@Test`, `@RepeatedTest`, `@ParameterizedTest`, and `@TestFactory` methods in the current class; analogous to JUnit 4's `@BeforeClass`; must be static (unless the "per-class" test instance lifecycle is used
- **`@AfterAll`**: Denotes that the annotated method should be executed after all `@Test`, `@RepeatedTest`, `@ParameterizedTest`, and `@TestFactory` methods in the current class; analogous to JUnit 4's @`AfterClass`; must be static (unless the "per-class" test instance lifecycle is used
- **`@Nested`**: Denotes that the annotated class is a non-static nested test class; `@BeforeAll` and `@AfterAll` methods cannot be used directly in a `@Nested` test class unless the "per-class" test instance lifecycle is used
- **`@Tag`**: Used to declare tags for filtering tests, either at the class or method level; analogous to test groups in TestNG or Categories in JUnit 4
- **`@Disabled`**: Used to disable a test class or test method; analogous to JUnit 4's `@Ignore`
- **`@Timeout`**: Used to fail a test, test factory, test template, or lifecycle method if its execution exceeds a given duration
- **`@ExtendWith`**: Used to register extensions declaratively
- **`@RegisterExtension`**: Used to register extensions programmatically via fields
- **`@TempDir`**: Used to supply a temporary directory via field injection or parameter injection in a lifecycle method or test method; located in the `org.junit.jupiter.api.io` package

### Meta-Annotations and Composed Annotations

- You can define your own composed annotation that will automatically inherit the semantics of its meta-annotations
- See:
  - [`annotations/FastTest.java`](src/test/java/com/jashburn/junit5/annotations/FastTest.java)
  - [`annotations/ComposedAnnotationUsage.java`](src/test/java/com/jashburn/junit5/annotations/ComposedAnnotationUsage.java)

## Test Classes and Methods

- **Test Class**: any top-level class, static member class, or `@Nested` class that contains at least one test method; test classes must not be abstract and must have a single constructor
- **Test Method**: any instance method that is directly annotated or meta-annotated with `@Test`, `@RepeatedTest`, `@ParameterizedTest`, `@TestFactory`, or `@TestTemplate`
- **Lifecycle Method**: any method that is directly annotated or meta-annotated with `@BeforeAll`, `@AfterAll`, `@BeforeEach`, or `@AfterEach`
- Test methods and lifecycle methods may be declared locally within the current test class, inherited from superclasses, or inherited from interfaces
  - must not be `abstract` and must not return a value
- Test classes, test methods, and lifecycle methods are not required to be `public`, but they must not be `private`
- See [`testclassesmethods/StandardTests.java`](src/test/java/com/jashburn/junit5/testclassesmethods/StandardTests.java)

## Display Names

- Test classes and test methods can declare custom display names via `@DisplayName` that will be displayed in test reports and by test runners and IDEs
  - can contain spaces, special characters, and even emojis
  - see [`displayname/DisplayNameDemo.java`](src/test/java/com/jashburn/junit5/displayname/DisplayNameDemo.java)

### Display Name Generators

- JUnit Jupiter supports custom display name generators that can be configured via `@DisplayNameGeneration`
- Values provided via `@DisplayName` always take precedence over display names generated by a `DisplayNameGenerator`
- See:
  - [`displayname/DisplayNameGeneratorReplaceUnderscores.java`](src/test/java/com/jashburn/junit5/displayname/DisplayNameGeneratorReplaceUnderscores.java)
  - [`displayname/CustomDisplayNameGenerator.java`](src/test/java/com/jashburn/junit5/displayname/CustomDisplayNameGenerator.java)
- You can use the `junit.jupiter.displayname.generator.default` configuration parameter (e.g., in `src/test/resources/junit-platform.properties`) to specify the fully qualified class name of the `DisplayNameGenerator` you would like to use by default, e.g.,

```properties
junit.jupiter.displayname.generator.default = \
    org.junit.jupiter.api.DisplayNameGenerator$ReplaceUnderscores
```

## Assertions

- All JUnit Jupiter assertions are static methods in the [`org.junit.jupiter.api.Assertions`](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html) class
- See:
  - [`assertions/AssertionsDemo.java`](src/test/java/com/jashburn/junit5/assertions/AssertionsDemo.java)
  - [`assertions/TimeoutAssertions.java`](src/test/java/com/jashburn/junit5/assertions/TimeoutAssertions.java)
- Third-party assertion libraries
  - when more power and additional functionality such as matchers are desired or required
    - e.g., for a combination of matchers and a fluent API to make assertions more descriptive and readable
  - use the built-in support for matchers provided by third-party assertion libraries such as AssertJ, Hamcrest, Truth, etc
  - e.g., as long as the Hamcrest library has been added to the classpath, you can statically import methods such as `assertThat()`, `is()`, and `equalTo()`, and then use them in tests

## Assumptions

- `Assumptions` is a collection of utility methods that support conditional test execution based on assumptions
  - all assumptions are static methods in the [`org.junit.jupiter.api.Assumptions`](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assumptions.html) class
- Failed assumptions do not result in a test failure; a failed assumption results in a test being aborted
- Assumptions are typically used whenever it does not make sense to continue execution of a given test method
  - e.g., if the test depends on something that does not exist in the current runtime environment
- See [`assumptions/AssumptionsDemo.java`](src/test/java/com/jashburn/junit5/assumptions/AssumptionsDemo.java)

## Conditional Test Execution

- Entire test classes or individual test methods may be disabled via the **`@Disabled`** annotation
- Other annotation-based conditions in the [`org.junit.jupiter.api.condition`](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/condition/package-summary.html) package allow developers to enable or disable containers and tests declaratively
  - operating system conditions: `@EnabledOnOs` and `@DisabledOnOs`
  - Java Runtime Environment conditions: `@EnabledOnJre` and `@DisabledOnJre`, `@EnabledForJreRange` and `@DisabledForJreRange`
  - JVM system property conditions: `@EnabledIfSystemProperty` and `@DisabledIfSystemProperty`
  - environment variable conditions: `@EnabledIfEnvironmentVariable` and `@DisabledIfEnvironmentVariable`

## Tagging

- Test classes and methods can be tagged via the **`@Tag`** annotation
- Tags can later be used to filter test discovery and execution
- Syntax rules for tags:
  - must not be null or blank
  - must not contain whitespace
  - must not contain ISO control characters
  - must not contain any of the following reserved characters:
    - comma (`,`)
    - left or right parenthesis (`(`, `)`)
    - ampersand (`&`)
    - vertical bar (`|`)
    - exclamation point (`!`)
- Example:

```java
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
@Tag("model")
class TaggingDemo {

    @Test
    @Tag("taxes")
    void testingTaxCalculation() {
    }

}
```

- See
  - [Filtering by Tags](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven-filter-tags): to specify in Maven the tests to execute
  - [Tag Expressions](https://junit.org/junit5/docs/current/user-guide/#running-tests-tag-expressions): to select which tests to execute

## Test Execution Order

- By default, test methods are ordered using an algorithm that is deterministic but intentionally non-obvious
- Although true unit tests typically should not rely on the order in which they are executed, there are times when it is necessary to enforce a specific test method execution order
  - when writing integration tests or functional tests where the sequence of the tests is important
  - in conjunction with `@TestInstance(Lifecycle.PER_CLASS)`
- To control the order in which test methods are executed
  - annotate your test class or test interface with `@TestMethodOrder`
  - specify the desired `MethodOrderer` implementation
    - implement your own custom MethodOrderer
    - `Alphanumeric`: sorts test methods alphanumerically based on their names and formal parameter lists
    - `OrderAnnotation`: sorts test methods numerically based on values specified via the `@Order` annotation
    - `Random`: orders test methods pseudo-randomly and supports configuration of a custom seed
- See [`testexecutionorder/OrderedTestsDemo.java`](src/test/java/com/jashburn/junit5/testexecutionorder/OrderedTestsDemo.java)

## Test Instance Lifecycle

- JUnit creates a new instance of each test class before executing each test method
  - to allow individual test methods to be executed in isolation
  - to avoid unexpected side effects due to mutable test instance state
  - i.e., the default mode is `Lifecycle.PER_METHOD`
- To execute all test methods on the same test instance
  - annotate test class with `@TestInstance(Lifecycle.PER_CLASS)`
  - a new test instance will be created once per test class
  - if your test methods rely on state stored in instance variables, you may need to reset that state in `@BeforeEach` or `@AfterEach` methods
  - some benefits over the default "per-method" mode:
    - possible to declare `@BeforeAll` and `@AfterAll` on non-static methods as well as on interface default methods
    - possible to use `@BeforeAll` and `@AfterAll` methods in `@Nested` test classes
- To change the default test instance lifecycle mode for the execution of an entire test plan
  - set the `junit.jupiter.testinstance.lifecycle.default` configuration parameter to the name of an enum constant defined in `TestInstance.Lifecycle`, ignoring case
    - JVM system property
      - `-Djunit.jupiter.testinstance.lifecycle.default=per_class`
    - configuration parameter in the `LauncherDiscoveryRequest` that is passed to the `Launcher`
    - JUnit Platform configuration file
      - in `junit-platform.properties` in the root of the class path (e.g., `src/test/resources`):
      - `junit.jupiter.testinstance.lifecycle.default = per_class`

## Nested Tests

- `@Nested` tests give the test writer more capabilities to express the relationship among several groups of tests
- Only non-static nested classes (i.e. inner classes) can serve as `@Nested` test classes
- Nesting can be arbitrarily deep
- Inner classes are considered to be full members of the test class family with one exception: `@BeforeAll` and `@AfterAll` methods do not work by default
- `@BeforeEach` and `@AfterEach` methods are called for each of the current and descendant test methods
- See [`nestedtests/TestingAStackDemo.java`](src/test/java/com/jashburn/junit5/nestedtests/TestingAStackDemo.java)

## Dependency Injection for Constructors and Methods

- All prior JUnit versions - test constructors or methods were not allowed to have parameters with the standard Runner implementations
- JUnit Jupiter - both test constructors and methods are permitted to have parameters
  - allows for greater flexibility and enables Dependency Injection for constructors and methods
- [`ParameterResolver`](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/extension/ParameterResolver.html)
  - defines the API for test extensions that wish to dynamically resolve parameters at runtime
  - resolve parameter at runtime for test class constructor, test method, and lifecycle method
  - 3 built-in resolvers are registered automatically
- [**`TestInfoParameterResolver`**](https://github.com/junit-team/junit5/tree/r5.6.2/junit-jupiter-engine/src/main/java/org/junit/jupiter/engine/extension/TestInfoParameterResolver.java)
  - for parameter type [`TestInfo`](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/TestInfo.html)
    - in `@Test`, `@RepeatedTest`, `@ParameterizedTest`, `@TestFactory`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`, and `@AfterAll` methods
  - supply an instance of `TestInfo` corresponding to the current container or test as the value for the parameter
    - provides information about the current container or test
      - display name (either a technical name - name of the test class or test method - or a custom name configured via `@DisplayName`)
      - test class
      - test method
      - associated tags
  - `TestInfo` acts as a drop-in replacement for the `TestName` rule from JUnit 4
  - see [`dependencyinjection/TestInfoDemo.java`](src/test/java/com/jashburn/junit5/dependencyinjection/TestInfoDemo.java)
- [**`RepetitionInfoParameterResolver`**](https://github.com/junit-team/junit5/tree/r5.6.2/junit-jupiter-engine/src/main/java/org/junit/jupiter/engine/extension/RepetitionInfoParameterResolver.java)
  - for parameter type [`RepetitionInfo`](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/RepetitionInfo.html)
    - in `@RepeatedTest`, `@BeforeEach`, or `@AfterEach` methods
  - supply an instance of `RepetitionInfo`
    - provides information about the current repetition and the total number of repetitions for the corresponding `@RepeatedTest`
  - note: `RepetitionInfoParameterResolver` is not registered outside the context of a `@RepeatedTest`
  - see 'Repeated Tests' for an example
- [**`TestReporterParameterResolver`**](https://github.com/junit-team/junit5/tree/r5.6.2/junit-jupiter-engine/src/main/java/org/junit/jupiter/engine/extension/TestReporterParameterResolver.java)
  - for parameter type [`TestReporter`](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/TestReporter.html)
    - in `@BeforeEach` and `@AfterEach` lifecycle methods as well as methods annotated with `@Test`, `@RepeatedTest`, `@ParameterizedTest`, `@TestFactory`, etc.
  - supply an instance of `TestReporter`
    - used to publish additional data about the current test run
    - data can be consumed via the `reportingEntryPublished()` method in a `TestExecutionListener`
      - viewed in IDEs or included in reports
  - use `TestReporter` where you used to print information to stdout or stderr in JUnit 4
    - using `@RunWith(JUnitPlatform.class)` will output all reported entries to stdout
  - see [`dependencyinjection/TestReporterDemo.java`](src/test/java/com/jashburn/junit5/dependencyinjection/TestReporterDemo.java)
- Custom parameter resolvers
  - must be explicitly enabled by registering appropriate extensions via `@ExtendWith`
  - see
    - [`dependencyinjection/RandomParametersExtension.java`](src/test/java/com/jashburn/junit5/dependencyinjection/RandomParametersExtension.java)
    - [`dependencyinjection/RandomParametersTest.java`](src/test/java/com/jashburn/junit5/dependencyinjection/RandomParametersTest.java)
  - when the type of the parameter to inject is the only condition for your `ParameterResolver`
    - use the generic [`TypeBasedParameterResolver`](https://github.com/junit-team/junit5/tree/r5.6.2/junit-jupiter-api/src/main/java/org/junit/jupiter/api/extension/support/TypeBasedParameterResolver.java) base class
    - the `supportsParameters` method is implemented behind the scenes and supports parameterized types

## Test Interfaces and Default Methods

- `@Test`, `@RepeatedTest`, `@ParameterizedTest`, `@TestFactory`, `@TestTemplate`, `@BeforeEach`, and `@AfterEach` can be declared on interface default methods
- `@BeforeAll` and `@AfterAll` can be declared either on static methods in a test interface or on interface default methods if the test interface or test class is annotated with `@TestInstance(Lifecycle.PER_CLASS)`
- See:
  - [`testinterfaces/TestLifecycleLogger.java`](src/test/java/com/jashburn/junit5/testinterfaces/TestLifecycleLogger.java)
  - [`testinterfaces/TestInterfaceUser.java`](src/test/java/com/jashburn/junit5/testinterfaces/TestInterfaceUser.java)
- `@ExtendWith` and `@Tag` can be declared on a test interface so that classes that implement the interface automatically inherit its tags and extensions
- Another possible application of this feature is to write tests for interface contracts
  - e.g., you can write tests for how implementations of `Object.equals` or `Comparable.compareTo` should behave
    - test class can then implement both contract interfaces thereby inheriting the corresponding tests
    - see:
      - [`testinterfaces/Testable.java`](src/test/java/com/jashburn/junit5/testinterfaces/Testable.java)
      - [`testinterfaces/EqualsContract.java`](src/test/java/com/jashburn/junit5/testinterfaces/EqualsContract.java)
      - [`testinterfaces/ComparableContract.java`](src/test/java/com/jashburn/junit5/testinterfaces/ComparableContract.java)
      - [`testinterfaces/RankTest.java`](src/test/java/com/jashburn/junit5/testinterfaces/RankTest.java)

## Repeated Tests

- Repeats a test a specified number of times by annotating a method with `@RepeatedTest` and specifying the total number of repetitions
- Each invocation of a repeated test behaves like the execution of a regular `@Test` method with full support for the same lifecycle callbacks and extensions
- A custom display name can be configured for each repetition via the `name` attribute of the `@RepeatedTest` annotation
  - display name can be a pattern composed of a combination of static text and dynamic placeholders
    - `{displayName}`: display name of the `@RepeatedTest` method
    - `{currentRepetition}`: the current repetition count
    - `{totalRepetitions}`: the total number of repetitions
  - default display name for a given repetition is generated based on: `"repetition {currentRepetition} of {totalRepetitions}"`
  - predefined `RepeatedTest.LONG_DISPLAY_NAME` pattern: `"{displayName} :: repetition {currentRepetition} of {totalRepetitions}"`
- Inject an instance of `RepetitionInfo` into `@RepeatedTest`, `@BeforeEach`, or `@AfterEach` method to retrieve information about the current repetition and the total number of repetitions
- See [`repeatedtests/RepeatedTestsDemo.java`](src/test/java/com/jashburn/junit5/repeatedtests/RepeatedTestsDemo.java)

## Parameterized Tests

- Parameterized tests make it possible to run a test multiple times with different arguments
- Needs dependency on the `junit-jupiter-params` artifact
  - included in the `junit-jupiter` artifact, otherwise add separately
- Declared just like regular `@Test` methods but use the `@ParameterizedTest` annotation instead
  - must declare at least one _source_ that will provide the arguments for each invocation, and then _consume_ the arguments in the test method

### Consuming Arguments

- Parameterized test methods typically consume arguments directly from the configured source following a one-to-one correlation between argument source index and method parameter index (as with `@CsvSource`)
- A parameterized test method may choose to aggregate arguments from the source into a single object passed to the method (see 'Argument Aggregation')
- Additional arguments may be provided by a `ParameterResolver`
- A parameterized test method must declare formal parameters according to the following rules:
  - Zero or more indexed arguments must be declared first
  - Zero or more aggregators must be declared next.
  - Zero or more arguments supplied by a `ParameterResolver` must be declared last.
- An aggregator is any parameter of type `ArgumentsAccessor` or any parameter annotated with `@AggregateWith`

### Sources of Arguments

- See [org.junit.jupiter.params.provider](https://junit.org/junit5/docs/current/api/org.junit.jupiter.params/org/junit/jupiter/params/provider/package-summary.html) package

#### `@ValueSource`

- Specifies a single array of literal values, and provide a single argument per parameterized test invocation
- Supported literal values: `short`, `byte`, `int`, `long`, `float`, `double`, `char`, `boolean`, `java.lang.String`, `java.lang.Class`
- See [`parameterizedtests/ValueSourceTests.java`](src/test/java/com/jashburn/junit5/parameterizedtests/ValueSourceTests.java)

#### Null and Empty Sources

- It can be useful to have null and empty values supplied to our parameterized tests
- The following annotations serve as sources of null and empty values for parameterized tests that accept a single argument:
  - `@NullSource`: provides a single null argument
    - cannot be used for a parameter that has a primitive type
  - `@EmptySource`: provides a single empty argument
    - for parameters of the following types: `java.lang.String`, `java.util.List`, `java.util.Set`, `java.util.Map`, primitive arrays (e.g., `int[]`, `char[][]`), object arrays (e.g., `String[]`, `Integer[][]`)
    - subtypes of the supported types are not supported
  - `@NullAndEmptySource`: a composed annotation that combines the functionality of `@NullSource` and `@EmptySource`
- Supply multiple varying types of blank strings by using `@ValueSource`
  - `@ValueSource(strings = {" ", " ", "\t", "\n"})`
- See [`parameterizedtests/ValueSourceTests.java`](src/test/java/com/jashburn/junit5/parameterizedtests/ValueSourceTests.java)

#### `@EnumSource`

- Provides a convenient way to use `Enum` constants
- `value` attribute is optional
  - when omitted, the declared type of the first method parameter is used
  - is required when the test method parameter is declared with an interface type
- `names` attribute is optional
  - lets you specify which constants shall be used
  - if omitted, all constants will be used
- `mode` attribute is optional
  - enables fine-grained control over which constants are passed to the test method
  - e.g., you can exclude names from the enum constant pool, or specify regular expressions
- See [`parameterizedtests/EnumSourceTests.java`](src/test/java/com/jashburn/junit5/parameterizedtests/EnumSourceTests.java)

#### `@MethodSource`

- Allows you to refer to one or more factory methods of the test class or external classes
- Factory methods
  - within the test class: must be `static` unless the test class is annotated with `@TestInstance(Lifecycle.PER_CLASS)`
  - in external classes: must always be `static`
  - must not accept any arguments
- Each factory method must generate a stream of arguments
  - stream: anything that JUnit can reliably convert into a `Stream`, such as `Stream`, `DoubleStream`, `Collection`, `Iterator`, `Iterable`, an array of objects, or an array of primitives
  - arguments: can be supplied as an instance of `Arguments`, an array of objects (e.g., `Object[]`), or a single value if the parameterized test method accepts a single argument
    - `Arguments`: an abstraction that provides access to an array of objects
- Examples
  - for all cases below, see [`parameterizedtests/MethodSourceTests.java`](src/test/java/com/jashburn/junit5/parameterizedtests/MethodSourceTests.java)
  - if you only need a **single parameter**, you can return a `Stream` of instances of the parameter type
    - see:
      - `explicitLocalMethodSourceStream()`
      - `explicitLocalMethodSourceArray()`
  - if you do not explicitly provide a **factory method name** via `@MethodSource`, JUnit Jupiter will search for a factory method that has the same name as the current `@ParameterizedTest` method
    - see `testWithDefaultLocalMethodSource()`
  - streams for **primitive types** (`DoubleStream`, `IntStream`, and `LongStream`) are also supported
    - see `testOddIntegers()`
  - if a parameterized test method declares **multiple parameters**, you need to return a collection, stream, or array of `Arguments` instances or object arrays
    - see:
      - `multiArgArgumentsStream()`
      - `multiArgArray()`
  - an **external, `static` factory method** can be referenced by providing its fully qualified method name
    - e.g., `@MethodSource("example.StringsProviders#tinyStrings")`

#### `@CsvSource`

- Allows you to express argument lists as comma-separated values (i.e., `String` literals)
- Default delimiter is a comma (`,`), but you can use another character by setting the `delimiter` attribute
  - `delimiterString` attribute allows you to use a String delimiter
  - `delimiter` and `delimiterString` attributes cannot be set simultaneously
- Uses a single quote (`'`) as its quote character
- An empty quoted value (`''`) results in an empty `String` unless the `emptyValue` attribute is set
- An entirely empty value is interpreted as a `null` reference
  - by specifying one or more `nullValues`, a custom value can be interpreted as a `null` reference
- See [`parameterizedtests/CsvSourceTests.java`](src/test/java/com/jashburn/junit5/parameterizedtests/CsvSourceTests.java)

#### `@CsvFileSource`

- Lets you use CSV files from the classpath
- Any line beginning with a `#` symbol will be interpreted as a comment and will be ignored
- Uses a double quote (`"`) as the quote character
- See:
  - [`parameterizedtests/CsvFileSourceTests.java`](src/test/java/com/jashburn/junit5/parameterizedtests/CsvFileSourceTests.java)
  - [`resources/csv_file_resource.csv`](src/test/resources/csv_file_resource.csv)

#### `@ArgumentsSource`

- To specify a custom, reusable `ArgumentsProvider`
  - an implementation of `ArgumentsProvider` must be declared as either a top-level class or as a `static` nested class
- See [`parameterizedtests/ArgumentsSourceTests.java`](src/test/java/com/jashburn/junit5/parameterizedtests/ArgumentsSourceTests.java)

### Argument Conversion

#### Widening Conversion

- JUnit Jupiter supports [Widening Primitive Conversion](https://docs.oracle.com/javase/specs/jls/se8/html/jls-5.html#jls-5.1.2) for arguments supplied to a `@ParameterizedTest`
  - e.g., a parameterized test annotated with `@ValueSource(ints = { 1, 2, 3 })` can be declared to accept not only an argument of type `int` but also an argument of type `long`, `float`, or `double`

#### Implicit Conversion

- To support use cases like `@CsvSource`, JUnit Jupiter provides a number of built-in implicit type converters
  - conversion process depends on the declared type of each method parameter
- `String` instances are implicitly converted to a number of target types
  - see <https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-argument-conversion-implicit>
- Fallback String-to-Object conversion
  - JUnit Jupiter also provides a fallback mechanism for automatic conversion from a `String` to a given target type if the target type declares exactly one suitable:
    - **factory method**
      - a non-private, `static` method declared in the target type that accepts a single `String` argument and returns an instance of the target type
      - the name of the method can be arbitrary
    - **factory constructor**
      - a non-private constructor in the target type that accepts a single `String` argument
      - the target type must be declared as either a top-level class or as a `static` nested class
    - if multiple factory methods are discovered, they will be ignored
    - if a factory method and a factory constructor are discovered, the factory method will be used instead of the constructor
  - see:
    - [`parameterizedtests/ImplicitStringToObjectConversion.java`](src/test/java/com/jashburn/junit5/parameterizedtests/ImplicitStringToObjectConversion.java)
    - [`parameterizedtests/Book.java`](src/main/java/com/jashburn/junit5/parameterizedtests/Book.java)

#### Explicit conversion

- You may explicitly specify an `ArgumentConverter` to use for a certain parameter using the `@ConvertWith` annotation
- An implementation of `ArgumentConverter` must be declared as either a top-level class or as a `static` nested class
- See [`parameterizedtests/ExplicitConversion.java`](src/test/java/com/jashburn/junit5/parameterizedtests/ExplicitConversion.java)
- `junit-jupiter-params` provides a single explicit argument converter that may also serve as a reference implementation: `JavaTimeArgumentConverter`
  - used via the composed annotation `JavaTimeConversionPattern`

### Argument Aggregation

- By default, each argument provided to a `@ParameterizedTest` method corresponds to a single method parameter
  - argument sources that are expected to supply a large number of arguments can lead to large method signatures
- An `ArgumentsAccessor` can be used instead of multiple parameters
  - access the provided arguments through a single argument passed to your test method
  - type conversion is supported as discussed in 'Implicit Conversion'
- See `argumentsAccessor()` in [`parameterizedtests/ArgumentAggregation.java`](src/test/java/com/jashburn/junit5/parameterizedtests/ArgumentAggregation.java)
- JUnit Jupiter also supports the usage of reusable **custom aggregators**
  - implement the `ArgumentsAggregator` interface and register it via the `@AggregateWith` annotation on a compatible parameter in the `@ParameterizedTest` method
  - result of the aggregation will then be provided as an argument for the corresponding parameter
  - an implementation of `ArgumentsAggregator` must be declared as either a top-level class or as a static nested class
    -see `customArgumentsAggregator()` in [`parameterizedtests/ArgumentAggregation.java`](src/test/java/com/jashburn/junit5/parameterizedtests/ArgumentAggregation.java)
- You can create a **custom composed annotation** such as `@CsvToMyType` that is meta-annotated with `@AggregateWith(MyTypeAggregator.class)`
  - see:
    - [`parameterizedtests/CsvToPerson.java`](src/test/java/com/jashburn/junit5/parameterizedtests/CsvToPerson.java)
    - `customAggregatorAnnotation()` in [`parameterizedtests/ArgumentAggregation.java`](src/test/java/com/jashburn/junit5/parameterizedtests/ArgumentAggregation.java)

### Customizing Display Names

- You can customize invocation display names via the `name` attribute of the `@ParameterizedTest` annotation
- See [`parameterizedtests/CustomDisplayNames.java`](src/test/java/com/jashburn/junit5/parameterizedtests/CustomDisplayNames.java)

## Dynamic Tests

- Dynamic tests are generated at runtime by factory methods that are annotated with `@TestFactory`
- [**`@TestFactory`**](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/TestFactory.html) method
  - not a test case but rather a factory for test cases
  - produces dynamic tests
  - must return a single `DynamicNode` or a `Stream`, `Collection`, `Iterable`, `Iterator`, or array of `DynamicNode` instances
  - (as with `@Test` methods) must not be private or `static`, and may optionally declare parameters to be resolved by `ParameterResolvers` (see 'Dependency Injection for Constructors and Methods')
- [**`DynamicNode`**](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/DynamicNode.html)
  - instantiable subclasses are `DynamicContainer` and `DynamicTest`
- [**`DynamicContainer`**](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/DynamicContainer.html)
  - composed of a display name and an `Iterable` or `Stream` of dynamic child nodes
  - enables the creation of arbitrarily nested hierarchies of dynamic nodes
- [**`DynamicTest`**](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/DynamicTest.html)
  - executed lazily
  - enables dynamic and even non-deterministic generation of test cases at runtime
  - composed of a display name and an `Executable`
    - `Executable` is a `@FunctionalInterface` - implementations of dynamic tests can be provided as lambda expressions or method references
- Any `Stream` returned by a `@TestFactory` will be properly closed by calling `stream.close()`
  - safe to use a resource such as `Files.lines()`
- Dynamic test lifecycle
  - quite different than it is for a standard `@Test` case
  - no lifecycle callbacks for individual dynamic tests
    - `@BeforeEach` and `@AfterEach` methods and their corresponding extension callbacks are executed for the `@TestFactory` method but not for each dynamic test
    - if you access fields from the test instance within a lambda expression, those fields will not be reset by callback methods or extensions between the execution of individual dynamic tests generated by the same `@TestFactory` method
- See [`dynamictests/DynamicTestsDemo.java`](src/test/java/com/jashburn/junit5/dynamictests/DynamicTestsDemo.java)

## Timeouts

- Allows to declare that a test, test factory, test template, or lifecycle method should fail if its execution time exceeds a given duration
- Time unit for the duration defaults to seconds but is configurable

```java
class TimeoutDemo {

    @BeforeEach
    @Timeout(5)
    void setUp() {
        // fails if execution time exceeds 5 seconds
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void failsIfExecutionTimeExceeds100Milliseconds() {
        // fails if execution time exceeds 100 milliseconds
    }
}
```

- Execution of the annotated method proceeds in the main thread of the test
  - if the timeout is exceeded, the main thread is interrupted from another thread
- To apply the same timeout to all test methods within a test class and all of its `@Nested` classes
  - declare the `@Timeout` annotation at the class level
  - applied to all test, test factory, and test template methods within that class and its `@Nested` classes
    - can overridden by a `@Timeout` annotation on a specific method or `@Nested` class
  - not applied to lifecycle methods
- Declaring `@Timeout` on a `@TestFactory` method checks that the factory method returns within the specified duration
  - does not verify the execution time of each individual `DynamicTest` generated by the factory
    - use `assertTimeout()` or `assertTimeoutPreemptively()`
- If `@Timeout` is present on a `@TestTemplate` method, e.g., a `@RepeatedTest` or `@ParameterizedTest`
  - each invocation will have the given timeout applied to it
- [Configuration parameters](https://junit.org/junit5/docs/current/user-guide/#writing-tests-declarative-timeouts) can be used to specify global timeouts

## Parallel Execution

- See <https://junit.org/junit5/docs/current/user-guide/#writing-tests-parallel-execution>

## Built-in Extensions

### The `TempDirectory` Extension

- The built-in `TempDirectory` extension is used to create and clean up a temporary directory for an individual test or all tests in a test class
  - registered by default
- Temporary directory creation
  - when a non-private field in a test class, or a parameter in a lifecycle method, or test method is annotated with `@TempDir`
  - for field or parameter of type `java.nio.file.Path` or `java.io.File`
- Temporary directory scope
  - depends on where the first `@TempDir` annotation is encountered when executing a test class
  - `@TempDir` on a `static` field, or on a parameter of a `@BeforeAll` method
    - shared by all tests in a class
  - `@TempDir` on instance fields, or on parameters in test, `@BeforeEach`, or `@AfterEach` methods
    - each test will use its own temporary directory
- Temporary directory deletion
  - when the end of the scope of a temporary directory is reached, i.e., when the test method or class has finished execution
- See [`tempdir/TempDirDemo.java`](src/test/java/com/jashburn/junit5/tempdir/TempDirDemo.java)

## Sources

- "JUnit 5 User Guide." <https://junit.org/junit5/docs/current/user-guide/>.
- "junit5/documentation." <https://github.com/junit-team/junit5/tree/master/documentation>.
- "JUnit 5 Tutorial: Writing Assertions With JUnit 5 Assertion API." <https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-writing-assertions-with-junit-5-api/>.
- "Assertions (JUnit 5.6.2 API)." <https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html>.
- "Assumptions (JUnit 5.6.2 API)." <https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assumptions.html>.
- "TempDir (JUnit 5.6.2 API)." <https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/io/TempDir.html>.
