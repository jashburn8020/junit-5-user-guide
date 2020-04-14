package com.jashburn.junit5.parameterizedtests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.jashburn.junit5.parameterizedtests.ArgumentAggregation.PersonAggregator;
import org.junit.jupiter.params.aggregator.AggregateWith;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@AggregateWith(PersonAggregator.class)
public @interface CsvToPerson {
}
