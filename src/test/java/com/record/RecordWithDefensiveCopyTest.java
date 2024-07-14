
package com.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The list parameter in the record has defensive copy on constructor and getter
 */
public class RecordWithDefensiveCopyTest {

    private List<String> inputList;
    private RecordWithDefensiveCopy record;

    @BeforeEach
    public void setUp() {
        // mutable list
        inputList = new ArrayList<>();
        inputList.add("Alice");
        inputList.add("Betty");
        inputList.add("Cathy");

        record = new RecordWithDefensiveCopy(inputList);
    }

    @Test
    public void defensiveCopyWorksOnConstructor() {
        assertEquals(3, record.list().size());
        inputList.add("Daniel");
        assertEquals(3, record.list().size());
    }

    @Test
    public void defensiveCopyWorksOnGetter() {
        assertThrows(UnsupportedOperationException.class,
                () -> record.list().add("Daniel"));
    }

}
