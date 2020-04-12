package com.jashburn.junit5.testinterfaces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestInterfaceUser implements TestLifecycleLogger {

    @DisplayName("Test One")
    @Test
    void test1() {
    }
}
