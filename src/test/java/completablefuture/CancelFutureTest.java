package completablefuture;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;

/**
 * We use future.cancel() to interrupt a thread.
 */
public class CancelFutureTest {

    @Test
    public void futureCancelShouldInterruptThread() throws InterruptedException {
        CountDownLatch waitLatch = new CountDownLatch(1);

        CompletableFuture<?> future = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Wait");
                waitLatch.await(); // cancel should interrupt
                System.out.println("Done");
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                throw new RuntimeException(e);
            } catch (CancellationException e2) {
                System.out.println("CancellationException is called");
            }
        });

        // the future is still running.
        assertFalse(future.isCancelled());
        assertFalse(future.isDone());

        future.cancel(true);
        System.out.println("Cancel called");

        // the future is cancelled.
        assertTrue(future.isCancelled());
        assertTrue(future.isDone());
    }

}
