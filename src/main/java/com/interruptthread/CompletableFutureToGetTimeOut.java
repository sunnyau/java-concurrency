package com.interruptthread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Use CompletableFuture get() with 5 seconds Timeout to interrupt a thread.
 */
public class CompletableFutureToGetTimeOut {

    public static void main(String[] args) {

        Runnable runnable = () -> {
            while (true) {
                System.out.println("thread is running");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException is thrown.");
                }
            }
        };

        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(runnable);

        try {
            System.out.println("Action : completableFuture.get( 5, TimeUnit.SECONDS)");
            completableFuture.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // No need to call completableFuture.cancel(true);
            System.err.println(e.toString());
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // no action is required.
        }

        System.out.println("End of main");
    }

}
