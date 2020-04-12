package com.jashburn.junit5.dependencyinjection;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

/**
 * Outputs:
 * 
 * <pre>
 * TestIdentifier [reportSingleValue(TestReporter)]
 * ReportEntry [timestamp = 2020-04-12T15:46:23.147524, value = 'initialise test']
 * TestIdentifier [reportSingleValue(TestReporter)]
 * ReportEntry [timestamp = 2020-04-12T15:46:23.156446, value = 'a status message']
 * TestIdentifier [reportSingleValue(TestReporter)]
 * ReportEntry [timestamp = 2020-04-12T15:46:23.159874, value = 'tear down test']
 * TestIdentifier [reportMultipleKeyValuePairs(TestReporter)]
 * ReportEntry [timestamp = 2020-04-12T15:46:23.168240, value = 'initialise test']
 * TestIdentifier [reportMultipleKeyValuePairs(TestReporter)]
 * ReportEntry [timestamp = 2020-04-12T15:46:23.169132, user name = 'dk38', award year = '1974']
 * TestIdentifier [reportMultipleKeyValuePairs(TestReporter)]
 * ReportEntry [timestamp = 2020-04-12T15:46:23.169866, value = 'tear down test']
 * TestIdentifier [reportKeyValuePair(TestReporter)]
 * ReportEntry [timestamp = 2020-04-12T15:46:23.171783, value = 'initialise test']
 * TestIdentifier [reportKeyValuePair(TestReporter)]
 * ReportEntry [timestamp = 2020-04-12T15:46:23.172303, a key = 'a value']
 * TestIdentifier [reportKeyValuePair(TestReporter)]
 * ReportEntry [timestamp = 2020-04-12T15:46:23.173037, value = 'tear down test']
 * </pre>
 */
class TestReporterDemo {

    @BeforeEach
    void setUp(TestReporter testReporter) {
        testReporter.publishEntry("initialise test");
    }

    @AfterEach
    void tearDown(TestReporter testReporter) {
        testReporter.publishEntry("tear down test");
    }

    @Test
    void reportSingleValue(TestReporter testReporter) {
        testReporter.publishEntry("a status message");
    }

    @Test
    void reportKeyValuePair(TestReporter testReporter) {
        testReporter.publishEntry("a key", "a value");
    }

    @Test
    void reportMultipleKeyValuePairs(TestReporter testReporter) {
        Map<String, String> values = new HashMap<>();
        values.put("user name", "dk38");
        values.put("award year", "1974");

        testReporter.publishEntry(values);
    }

}
