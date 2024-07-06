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
 * Ref
 * https://www.baeldung.com/java-start-two-threads-at-same-time
 * 
 * Note : As we want to start all threads at the same time and want to wait all
 * threads to finish before counting the count, we use CyclicBarrier.
 * 
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
                    String thisThreadName = Thread.currentThread().getName();

                    System.out.println(thisThreadName + " is waiting to run.");
                    barrier.await(); // The thread keeps waiting till the last barrier is open
                    System.out.println(thisThreadName + " starts running and is waiting to finish.");

                    unsafeCounter.increment();
                    
                    barrier.await(); // The thread keeps waiting till the last barrier is open
                    System.out.println(thisThreadName + " is finished.");

                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();
        }

        // sleep for 2 seconds. ( The output message looks better. )
        TimeUnit.SECONDS.sleep(2);

        System.out.println("=== open the last barrier ===");
        barrier.await();

        // sleep for 2 seconds. ( The output message looks better. )
        TimeUnit.SECONDS.sleep(2);

        System.out.println("=== open the last barrier ===");
        barrier.await();

        assertEquals(THREAD_COUNT, unsafeCounter.getCount());
    }
}
