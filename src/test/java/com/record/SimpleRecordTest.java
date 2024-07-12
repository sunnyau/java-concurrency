package com.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SimpleRecordTest {

    @Test
    public void simpleRecordTest() {
        SimpleRecord record = new SimpleRecord("John", "Smith");
        assertEquals( "John", record.firstName());
        assertEquals( "Smith", record.lastName());
    }
}
