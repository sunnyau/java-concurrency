package com.threadsafety;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.threadsafety.SafeCounter;

/**
 * 

 * https://www.baeldung.com/java-start-two-threads-at-same-time
 * 
 * Note : As we want to start all threads at the same time and want to wait all
 * threads to finish before counting the count, we use CyclicBarrier.
 * 
 */
public class CyclicBarrierTest {

    public static final int THREAD_COUNT = 10;

    @Test
    public void countShouldEqualsToThreadNumber() throws InterruptedException, BrokenBarrierException {

        SafeCounter safeCounter = new SafeCounter();
        CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1); // +1 for main thread

        for (int i = 0; i < THREAD_COUNT; i++) {
            Runnable task = () -> {
                try {
                    String thisThreadName = Thread.currentThread().getName();

                    System.out.println(thisThreadName + " is waiting to run.");
                    barrier.await(); // The thread keeps waiting till the last barrier is open
                    System.out.println(thisThreadName + " starts running and is waiting to finish.");

                    safeCounter.increment();
                    // let allow the thread to take some time to do some works. 
                    TimeUnit.SECONDS.sleep(1);
                    
                    barrier.await(); // The thread keeps waiting till the last barrier is open
                    System.out.println(thisThreadName + " is finished.");

                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            };

            Thread thread = new Thread(task);
            thread.start();
        }

        // sleep for 2 seconds. ( The output message looks better. )
        TimeUnit.SECONDS.sleep(2);

        System.out.println("=== open the last barrier to start all threads ===");
        barrier.await();

        // sleep for 2 seconds. ( The output message looks better. )
        TimeUnit.SECONDS.sleep(2);

        System.out.println("=== open the last barrier to finish all threads ===");
        barrier.await();

        assertEquals(THREAD_COUNT, safeCounter.getCount());
    }
}
