package com.jashburn.junit5.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Custom <code>{@literal @}FastTest</code> annotation that can be used as a drop-in replacement for
 * <code>{@literal @}Tag("fast")</code> and <code>{@literal @}Test</code>.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Tag("fast")
@Test
public @interface FastTest {
}
