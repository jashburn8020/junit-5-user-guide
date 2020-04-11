# JUnit 5 User Guide

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

## Sources

- "JUnit 5 User Guide." <https://junit.org/junit5/docs/current/user-guide/>.
- "junit5/documentation." <https://github.com/junit-team/junit5/tree/master/documentation>.
- "JUnit 5 Tutorial: Writing Assertions With JUnit 5 Assertion API." <https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-writing-assertions-with-junit-5-api/>.
- "Assertions (JUnit 5.6.2 API)." <https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html>.
- "Assumptions (JUnit 5.6.2 API). "<https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assumptions.html>.
