package threadsafety;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * This test starts a number of threads to increment UnsafeCounter.
 * After incrementing by all threads, the counter should be the same as
 * THREAD_COUNT.
 * 
 * However as UnsafeCounter is not thread safe, it causes race condition.
 * 
 * The result is that SOMETIMES this test fails.
 * 
 * Ref : https://www.baeldung.com/java-start-two-threads-at-same-time
 */
public class UnsafeCounterWithCyclicBarrierTest {

    // public static final int THREAD_COUNT = 10000;
    public static final int THREAD_COUNT = 10;

    @Test
    public void countShouldEqualsToThreadNumber() throws InterruptedException, BrokenBarrierException {

        UnsafeCounter unsafeCounter = new UnsafeCounter();
        CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1); // +1 for main thread

        for (int i = 0; i < THREAD_COUNT; i++) {
            Runnable runnable = () -> {
                try {
                    // The thread keeps waiting till it is informed
                    barrier.await();
                    unsafeCounter.increment();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();
        }

        // sleep for 2 seconds
        TimeUnit.SECONDS.sleep(2);

        System.out.println("barrier.getNumberWaiting() BEFORE " + barrier.getNumberWaiting());
        // open the last barrier
        barrier.await();
        System.out.println("barrier.getNumberWaiting() AFTER  " + barrier.getNumberWaiting());

        // sleep for 2 seconds to let all threads to complete
        TimeUnit.SECONDS.sleep(2);

        assertEquals(THREAD_COUNT, unsafeCounter.getCount());
    }
}
