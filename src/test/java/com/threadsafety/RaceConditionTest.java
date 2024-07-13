package com.threadsafety;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;

/**
 * As UnsafeCounter, which is not thread-safe, is shared by two threads, it
 * causes race condition. Therefore the total count is less than 5000000.
 * 
 * In the same condition, SafeCounter is thread-safe. Therefore the total count
 * is exactly 5000000.
 */
public class RaceConditionTest {

    public static final int THREAD_SIZE = 5;
    public static final int EXPECTED_TOTAL = 5000000;

    @Test
    public void unsafeCounterCausesRaceConditionTest() throws InterruptedException {

        UnsafeCounter unsafeCounter = new UnsafeCounter();
        CountDownLatch endLatch = new CountDownLatch(THREAD_SIZE);

        Runnable task = () -> {
            unsafeCounter.incrementMillionTimes();
            endLatch.countDown();
        };

        for (int i = 0; i < THREAD_SIZE; i++) {
            new Thread(task).start();
        }

        // wait until both threads have ended.
        endLatch.await();

        int totalCount = unsafeCounter.getCount();
        System.out.println("unsafeCounter.getCount() is " + totalCount);
        assertTrue(totalCount < EXPECTED_TOTAL);
    }

    @Test
    public void safeCounterDoesNotCauseRaceConditionTest() throws InterruptedException {

        SafeCounter safeCounter = new SafeCounter();
        CountDownLatch endLatch = new CountDownLatch(THREAD_SIZE);

        Runnable task = () -> {
            safeCounter.incrementMillionTimes();
            endLatch.countDown();
        };

        for (int i = 0; i < THREAD_SIZE; i++) {
            new Thread(task).start();
        }

        // wait until both threads have ended.
        endLatch.await();

        int totalCount = safeCounter.getCount();
        System.out.println("safeCounter.getCount() is " + totalCount);
        assertTrue(totalCount == EXPECTED_TOTAL);
    }
}
