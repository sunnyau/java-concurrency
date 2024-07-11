package interruptthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TO DO 
 */
public class ScheduledToInterrupt {

    public static void main(String[] args) throws InterruptedException {

        final AtomicBoolean running = new AtomicBoolean(false);

        ExecutorService es = Executors.newFixedThreadPool(2);

        Runnable runnable = () -> {
            running.set(true);
            while (running.get()) {
                System.out.println("thread is running " + running.get());
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

        // try {
        //     TimeUnit.SECONDS.sleep(5);
        // } catch (InterruptedException e) {
        //     // no action is required.
        // }

        // System.out.println("Action : AtomicBoolean.set(false)");
        // running.set(false);

        // System.out.println("End of main");


        // ExecutorService es = Executors.newFixedThreadPool(2);


        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        

        System.out.println("Action : scheduler.schedule( runnable, 5, TimeUnit.SECONDS)");
        scheduler.schedule( () -> { running.set(false); }, 5, TimeUnit.SECONDS);



        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            // no action is required.
        }

        // System.out.println("Action : AtomicBoolean.set(false)");
        // running.set(false);

        System.out.println("End of main");
    }

}
