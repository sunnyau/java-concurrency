package com.threadsafety;

import org.junit.jupiter.api.Test;

import com.threadsafety.SafeCounter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit Test Using JUnit
 * Here's how you can write a unit test to test the thread safety of the
 * SafeCounter class using JUnit and CountDownLatch.
 * 
 * Explanation
 * 
 * NUM_THREADS: Defines the number of threads that will be used to increment the
 * counter.
 * CountDownLatch: Used to synchronize the start and end of thread execution.
 * startLatch ensures all threads start at the same time, and endLatch waits for
 * all threads to finish.
 * ExecutorService: Manages the pool of threads.
 * Assertions: Validates the final count to ensure no increments were lost due
 * to race conditions.
 * 
 */

public class SafeCounterTest {

    @Test
    public void testThreadSafety() throws InterruptedException {
        final int NUM_THREADS = 1000;
        final SafeCounter counter = new SafeCounter();
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(NUM_THREADS);
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.execute(() -> {
                try {
                    startLatch.await(); // Wait for the start signal
                    counter.increment();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    endLatch.countDown(); // Indicate that this thread is done
                }
            });
        }

        startLatch.countDown(); // Signal all threads to start
        endLatch.await(10, TimeUnit.SECONDS); // Wait for all threads to finish

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        // Verify the final count
        assertEquals(NUM_THREADS, counter.getCount(), "Counter value should match the number of increments");
    }
}
