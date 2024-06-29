package threadsafe;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

