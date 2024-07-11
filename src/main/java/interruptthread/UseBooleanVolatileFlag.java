package interruptthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Use a flag ( volatile boolean or AtomicBoolean ) to interrupt a thread.
 * 
 * This thread runs for 5 seconds and then gets interrupted by changing the flag.
 */
public class UseBooleanVolatileFlag {

    public static void main(String[] args) throws InterruptedException {

        final AtomicBoolean running = new AtomicBoolean(false);

        ExecutorService es = Executors.newFixedThreadPool(2);

        Runnable runnable = () -> {
            running.set(true);
            while (running.get()) {
                System.out.println("thread is running");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException is thrown.");
                }
            }
        };

        es.submit(runnable);

        // accept no more task
        es.shutdown();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            // no action is required.
        }

        System.out.println("Action : AtomicBoolean.set(false)");
        running.set(false);

        System.out.println("End of main");
    }
}
