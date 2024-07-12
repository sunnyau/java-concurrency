package com.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class MinMaxSumAverageTest {

    private List<Integer> numList = List.of(2, 3, 4, 5, 6);

    @Test
    public void reduceMinMaxSumOptional() {
        assertEquals(Optional.of(2), numList.stream().reduce(Integer::min));
        assertEquals(Optional.of(6), numList.stream().reduce(Integer::max));
        assertEquals(Optional.of(20), numList.stream().reduce(Integer::sum));
    }

    @Test
    public void reduceMinMaxSum() {
        // interesting. it is 0, not 2
        assertEquals(0, (int) numList.stream().reduce(0, Integer::min));
        assertEquals(6, (int) numList.stream().reduce(0, Integer::max));
        assertEquals(20, (int) numList.stream().reduce(0, Integer::sum));
    }

    /**
     * By default, min and max return Optional Int
     */
    @Test
    public void minMaxSumAverage() {
        assertEquals(2, numList.stream().mapToInt(Integer::valueOf).min().orElse(0));
        assertEquals(6, numList.stream().mapToInt(Integer::valueOf).max().orElse(0));
        assertEquals(20, numList.stream().mapToInt(Integer::valueOf).sum());
        assertEquals(4.0, numList.stream().mapToInt(Integer::valueOf).average().orElse(0.0), 0.01);
    }

    @Test
    public void IntStreamTest() {
        assertEquals(10, IntStream.rangeClosed(1, 10).count());
        assertEquals(9, IntStream.range(1, 10).count());
    }

}
