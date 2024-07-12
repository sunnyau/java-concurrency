
package com.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Record itself is immutable. But if the parameter is mutable, the whole record is still mutable.
 */
public class RecordWithListTest {

    @Test
    public void recordWithListTest() {
        // mutable list
        List<String> list = new ArrayList<>();
        list.add("Alice");
        list.add("Betty");
        list.add("Cathy");

        RecordWithList record = new RecordWithList(list);
        assertEquals(3, record.list().size());

        list.add("Daniel");
        assertEquals(4, record.list().size());
    }
}
