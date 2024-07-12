package com.interruptthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Use a Future cancel to interrupt a thread.
 * 
 * This thread runs for 5 seconds and then gets interrupted by calling Future.cancel().
 */
public class FutureCancel {

    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(2);

        Callable<String> callable = () -> {
            while(!Thread.currentThread().isInterrupted()) {
                System.out.println("thread is running");
                Thread.sleep(1000);
            }
            return "End of Thread";
        };

        Future<String> future = es.submit(callable);

        // accept no more task
        es.shutdown(); 

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            // no action is required.
        }

        System.out.println("Action : future.cancel(true)");
        future.cancel(true);

        System.out.println("End of main");
    }
}
