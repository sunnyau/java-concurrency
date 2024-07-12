package com.interruptthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Use a Future cancel to interrupt a thread.
 * 
 * The future get() waits for 5 seconds and then call cancel() to interrupt a thread.
 */
public class FutureGetThenCancel {

        public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(2);

        Callable<String> callable = () -> {
            // while(!Thread.currentThread().isInterrupted()) {
            while(true) {                   
                System.out.println("thread is running");
                Thread.sleep(1000);
            }
            // return "End of Thread";
        };

        Future<String> future = es.submit(callable);

        // accept no more task
        es.shutdown(); 

        try {
            System.out.println("Action : future.get( 5, TimeUnit.SECONDS)");            
            future.get( 5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Action : future.cancel(true)");            
            future.cancel(true);
        }

        System.out.println("End of main");
    }
}
