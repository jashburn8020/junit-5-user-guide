package com.jashburn.junit5.assertions;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class TimeoutAssertions {

    private final Person simpleJaneDoe = new Person("Jane", "Doe");

    /**
     * Assert that execution of the supplied executable completes before the given timeout is
     * exceeded. The executable will be executed in the <em>same</em> thread as that of the calling
     * code. Consequently, execution of the executable will not be preemptively aborted if the
     * timeout is exceeded.
     * <p>
     * Uses
     * {@link Assertions#assertTimeout(java.time.Duration, org.junit.jupiter.api.function.Executable)}.
     */
    @Test
    void timeoutNotExceeded() {
        assertTimeout(ofMinutes(2), () -> {
            // Perform task that takes less than 2 minutes.
        });
    }

    /**
     * Assert that timeout is not exceeded on an operation, and check operation's return value.
     * <p>
     * Uses
     * {@link Assertions#assertTimeout(java.time.Duration, org.junit.jupiter.api.function.ThrowingSupplier)}.
     */
    @Test
    void timeoutNotExceededWithResult() {
        String actualResult = assertTimeout(ofMinutes(2), () -> {
            return simpleJaneDoe.getFirstName();
        });
        assertEquals("Jane", actualResult);
    }

    /**
     * Assert that timeout is not exceeded on an operation invoked as a method reference.
     */
    @Test
    void timeoutNotExceededWithMethod() {
        String actualGreeting = assertTimeout(ofMinutes(2), simpleJaneDoe::greeting);
        assertEquals("Hi! I'm Jane", actualGreeting);
    }

    /**
     * This timeout assertion fails with an error message similar to:
     * <code>execution exceeded timeout of 10 ms by 90 ms</code>.
     */
    @Test
    void timeoutExceeded() {
        assertTimeout(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }

    /**
     * This variant of timeout assertion executes the executable in a <em>different</em> thread than
     * that of the calling code. Execution of the executable will be <em>preemptively aborted</em>
     * if the timeout is exceeded. This can lead to undesirable side effects if the code that is
     * executed within the executable (or supplier) relies on <code>ThreadLocal</code> storage.
     * <p>
     * This timeout assertion fails with an error message that looks like:
     * <code>execution timed out after 10 ms</code>.
     */
    @Test
    void timeoutExceededWithPreemptiveTermination() {
        assertTimeoutPreemptively(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            new CountDownLatch(1).await();
        });
    }

    /**
     * The difference between <code>assertTimeout()</code> and
     * <code>assertTimeoutPreemptively()</code> in terms of aborting the execution of the executable
     * on timeout. The former does not abort (and therefore runs for the whole 100 ms), while the
     * latter does.
     */
    @Test
    void timeoutPreemptivelyDifference() {
        assertAll(() -> {
            long startTime = System.currentTimeMillis();
            assertThrows(AssertionFailedError.class, () -> {
                assertTimeout(ofMillis(50), () -> {
                    Thread.sleep(100);
                });
            });

            assertTrue(elapsedTime(startTime) >= 100);
        }, () -> {
            long startTime = System.currentTimeMillis();
            assertThrows(AssertionFailedError.class, () -> {
                assertTimeoutPreemptively(ofMillis(50), () -> {
                    Thread.sleep(100);
                });
            });

            assertTrue(elapsedTime(startTime) < 99);
        });
    }

    private long elapsedTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }
}
