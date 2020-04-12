package com.jashburn.junit5.nestedtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EmptyStackException;
import java.util.Stack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Demonstration of nested tests.
 * <p>
 * The console output is as follows:
 * 
 * <pre>
 * (BeforeEach) createNewStack
 * - AfterPushing (BeforeEach): pushAnElement
 * - AfterPushing: returnElementWhenPeeked
 * - AfterPushing (AfterEach): doNothingAfterPushing
 * (AfterEach): doNothing
 * (BeforeEach) createNewStack
 * - AfterPushing (BeforeEach): pushAnElement
 * - AfterPushing: returnElementWhenPopped
 * - AfterPushing (AfterEach): doNothingAfterPushing
 * (AfterEach): doNothing
 * (BeforeEach) createNewStack
 * - AfterPushing (BeforeEach): pushAnElement
 * - AfterPushing: isNotEmpty
 * - AfterPushing (AfterEach): doNothingAfterPushing
 * (AfterEach): doNothing
 * (BeforeEach) createNewStack
 * - WhenNew: throwsExceptionWhenPeeked
 * (AfterEach): doNothing
 * (BeforeEach) createNewStack
 * - WhenNew: throwsExceptionWhenPopped
 * (AfterEach): doNothing
 * (BeforeEach) createNewStack
 * - WhenNew: isEmpty
 * (AfterEach): doNothing
 * </pre>
 */
@DisplayName("A stack")
class TestingAStackDemo {

    Stack<Object> stack;

    // Called before each of the descendants' test methods
    @BeforeEach
    void createNewStack() {
        System.out.println("(BeforeEach) createNewStack");
        stack = new Stack<>();
    }

    // Called after each of the descendants' test methods
    @AfterEach
    void doNothing() {
        System.out.println("(AfterEach): doNothing");
    }

    @Nested
    @DisplayName("Stack behaviour when new")
    class WhenNew {

        /*
         * Note necessary to instantiate a new Stack here - parent @BeforeEach is called when each
         * of the test methods in this nested class is executed.
         */

        @Test
        @DisplayName("is empty")
        void isEmpty() {
            System.out.println("- WhenNew: isEmpty");
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("throws EmptyStackException when popped")
        void throwsExceptionWhenPopped() {
            System.out.println("- WhenNew: throwsExceptionWhenPopped");
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("throws EmptyStackException when peeked")
        void throwsExceptionWhenPeeked() {
            System.out.println("- WhenNew: throwsExceptionWhenPeeked");
            assertThrows(EmptyStackException.class, stack::peek);
        }
    }

    @Nested
    @DisplayName("Stack behaviour after pushing an element")
    class AfterPushing {

        private String anElement = "an element";

        @BeforeEach
        void pushAnElement() {
            System.out.println("- AfterPushing (BeforeEach): pushAnElement");
            stack.push(anElement);
        }

        @AfterEach
        void doNothingAfterPushing() {
            System.out.println("- AfterPushing (AfterEach): doNothingAfterPushing");
        }

        @Test
        @DisplayName("it is no longer empty")
        void isNotEmpty() {
            System.out.println("- AfterPushing: isNotEmpty");
            assertFalse(stack.isEmpty());
        }

        @Test
        @DisplayName("returns the element when popped and is empty")
        void returnElementWhenPopped() {
            System.out.println("- AfterPushing: returnElementWhenPopped");
            assertEquals(anElement, stack.pop());
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("returns the element when peeked but remains not empty")
        void returnElementWhenPeeked() {
            System.out.println("- AfterPushing: returnElementWhenPeeked");
            assertEquals(anElement, stack.peek());
            assertFalse(stack.isEmpty());
        }
    }
}
