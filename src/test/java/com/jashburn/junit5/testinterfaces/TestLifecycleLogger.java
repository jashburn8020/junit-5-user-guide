package com.jashburn.junit5.testinterfaces;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

interface TestLifecycleLogger {

    @BeforeEach
    default void beforeEachTest(TestReporter reporter, TestInfo info) {
        reporter.publishEntry(String.format("About to execute [%s]", info.getDisplayName()));
    }

    @AfterEach
    default void afterEachTest(TestReporter reporter, TestInfo info) {
        reporter.publishEntry(String.format("Finished executing [%s]", info.getDisplayName()));
    }

}
