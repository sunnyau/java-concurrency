package threadsafety;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
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
 * Ref : https://www.baeldung.com/java-start-two-threads-at-same-time
 */
public class UnsafeCounterWithCountDownLatchTest {

    // public static final int THREAD_COUNT = 10000;
    public static final int THREAD_COUNT = 10;

    @Test
    public void countShouldEqualsToThreadNumber() throws InterruptedException, BrokenBarrierException {

        List<Thread> list = new LinkedList<>();
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            Runnable runnable = () -> {
                unsafeCounter.increment();
                latch.countDown();
            };
            list.add(new Thread(runnable));
        }

        // start all threads at the same time
        list.forEach(t -> t.start());

        // wait all threads to complete
        latch.await();

        assertEquals(THREAD_COUNT, unsafeCounter.getCount());
    }
}
