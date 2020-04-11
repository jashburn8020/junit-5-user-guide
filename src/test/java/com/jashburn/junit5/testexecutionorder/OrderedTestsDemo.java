package com.jashburn.junit5.testexecutionorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderedTestsDemo {

    private int state;

    @BeforeAll
    void setUpState() {
        state = 1;
    }

    @Test
    @Order(1)
    void changeStateToTwo() {
        assertEquals(1, state, () -> "State is not 1");
        state = 2;
    }

    @Test
    @Order(2)
    void changeStateToThree() {
        assertEquals(2, state, () -> "State is not 2");
        state = 3;
    }
}
