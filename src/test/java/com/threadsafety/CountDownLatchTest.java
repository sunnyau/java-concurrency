package com.threadsafety;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.threadsafety.SafeCounter;

/**
 * As we want to start all threads at the same time and want to wait all
 * threads to finish before counting the count, we use TWO countDownLatch(s) :
 * startLatch and endLatch
 * 
 */
public class CountDownLatchTest {

    public static final int THREAD_COUNT = 10;

    @Test
    public void countShouldEqualsToThreadNumber() throws InterruptedException, BrokenBarrierException {

        SafeCounter safeCounter = new SafeCounter();

        // we use two countdownLatch(s).
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            Runnable runnable = () -> {
                try {
                    // we wait until startLatch count is 0.
                    System.out.println("Thread is waiting to start");
                    startLatch.await();

                    safeCounter.increment(); 
                    // let allow the thread to take some time to do some works. 
                    TimeUnit.SECONDS.sleep(1);

                    endLatch.countDown();
                    System.out.println("Thread has ended");                      
                } catch (InterruptedException e) {
                    // Auto-generated catch block
                    e.printStackTrace();
                }
            };
            new Thread(runnable).start();
        }

        // let all threads start at the same time
        startLatch.countDown();

        // wait all threads to complete
        endLatch.await();

        assertEquals(THREAD_COUNT, safeCounter.getCount());
    }
}
