package threadsafety;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

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
 * Ref :
 * https://www.baeldung.com/java-start-two-threads-at-same-time
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CountDownLatch.html
 * 
 * Note : As we want to start all threads at the same time and want to wait all
 * threads to finish before counting the count, we use TWO countDownLatch(s) :
 * startLatch and endLatch
 * 
 */
public class UnsafeCounterWithCountDownLatchTest {

    // public static final int THREAD_COUNT = 10000;
    public static final int THREAD_COUNT = 10;

    @Test
    public void countShouldEqualsToThreadNumber() throws InterruptedException, BrokenBarrierException {

        // List<Thread> list = new LinkedList<>();
        UnsafeCounter unsafeCounter = new UnsafeCounter();

        // we use two countdownLatch(s).
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            Runnable runnable = () -> {
                try {
                    // we wait until startLatch count is 0.
                    startLatch.await();
                    unsafeCounter.increment();                    
                    endLatch.countDown();
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

        assertEquals(THREAD_COUNT, unsafeCounter.getCount());
    }
}
