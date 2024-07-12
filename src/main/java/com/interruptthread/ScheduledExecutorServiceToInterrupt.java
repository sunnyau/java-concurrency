package com.interruptthread;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Use a Future cancel to interrupt a thread.
 * 
 * This class uses ScheduledExecutorService to wait for 5 seconds and then call
 * future to interrupt the thread.
 */
public class ScheduledExecutorServiceToInterrupt {

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        Callable<String> callable = () -> {
            // while (!Thread.currentThread().isInterrupted()) {
            while (true) {
                System.out.println("thread is running");
                Thread.sleep(1000);
            }
            // return "End of Thread";
        };

        Future<String> future = scheduler.submit(callable);

        Runnable cancelTask = () -> {
            future.cancel(true);
        };

        System.out.println("Action : scheduler.schedule( cancelTask, 5, TimeUnit.SECONDS)");
        scheduler.schedule(cancelTask, 5, TimeUnit.SECONDS);
        scheduler.shutdown();

        System.out.println("End of main");
    }

}
