package com.jashburn.junit5.tempdir;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.io.TempDir;

class TempDirDemo {

    private Path tmp;

    @BeforeEach
    void setUp() {
        assertNull(tmp);
    }

    @AfterEach
    void tearDown() {
        assertTrue(Files.exists(tmp));
    }

    @Test
    void testTempDir(@TempDir Path tempDir, TestReporter reporter) {
        reporter.publishEntry(tempDir.toString());
        assertTrue(Files.exists(tempDir));
        tmp = tempDir;
    }
}
