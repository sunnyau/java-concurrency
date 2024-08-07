package com.interruptthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Use a shutDownNow() to interrupt a thread.
 * 
 * This thread runs for 5 seconds and then gets interrupted by changing the
 * flag.
 */
public class ShutDownNow {

    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(2);

        // note to myself : TimeUnit.SECONDS.sleep(1) does not need to use try-catch in
        // Callable but needs try-catch in Runnable.
        Callable<String> callable = () -> {
            // while (!Thread.currentThread().isInterrupted()) {
            while (true) {
                System.out.println("thread is running");
                Thread.sleep(1000);
            }
            // return "End of Thread";
        };

        es.submit(callable);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            // no action is required.
        }

        // accept no more task and stop any current tasks
        System.out.println("Action : ExecutorService.shutdownNow()");
        es.shutdownNow();

        System.out.println("End of main");
    }

}
