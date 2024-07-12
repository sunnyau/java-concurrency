package com.threadsafety;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;

import com.threadsafety.SafeCounter;
import com.threadsafety.UnsafeCounter;

/**
 * As UnsafeCounter, which is not thread-safe, is shared by two threads, it
 * causes race condition. Therefore the total count is less than 2000000.
 * 
 * In the same condition, SafeCounter is thread-safe. Therefore the total count
 * is exactly 2000000.
 */
public class RaceConditionTest {

    @Test
    public void unsafeCounterCausesRaceConditionTest() throws InterruptedException {

        UnsafeCounter unsafeCounter = new UnsafeCounter();
        CountDownLatch endLatch = new CountDownLatch(2);

        Runnable runnable = () -> {
            unsafeCounter.incrementMillionTimes();
            endLatch.countDown();
        };

        new Thread(runnable).start();
        new Thread(runnable).start();

        // wait until both threads have ended.
        endLatch.await();

        int totalCount = unsafeCounter.getCount();
        System.out.println("unsafeCounter.getCount() is " + totalCount);
        assertTrue(totalCount < 2000000);
    }

    @Test
    public void safeCounterDoesNotCauseRaceConditionTest() throws InterruptedException {

        SafeCounter safeCounter = new SafeCounter();
        CountDownLatch endLatch = new CountDownLatch(2);

        Runnable runnable = () -> {
            safeCounter.incrementMillionTimes();
            endLatch.countDown();
        };

        new Thread(runnable).start();
        new Thread(runnable).start();

        // wait until both threads have ended.
        endLatch.await();

        int totalCount = safeCounter.getCount();
        System.out.println("safeCounter.getCount() is " + totalCount);
        assertTrue(totalCount == 2000000);
    }
}
