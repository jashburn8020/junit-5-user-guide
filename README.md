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
- See [`testClassesAndMethods/StandardTests.java`](src/test/java/com/jashburn/junit5/testClassesAndMethods/StandardTests.java)

## Display Names

## Sources

- "JUnit 5 User Guide." <https://junit.org/junit5/docs/current/user-guide/>.
