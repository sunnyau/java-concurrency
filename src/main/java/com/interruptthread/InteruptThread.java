package com.interruptthread;

import java.util.concurrent.TimeUnit;

/**
 * Use a thread.interrupt() to interrupt a thread.
 * 
 * This thread runs for 5 seconds and then gets interrupted by changing the
 * flag.
 */
public class InteruptThread extends Thread {

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("thread is running");
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("InterruptedException is thrown.");
        }
    }

    public static void main(String[] args) {

        InteruptThread thread = new InteruptThread();
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            // no action is required.
        }

        System.out.println("Action : thread.interrupt()");
        thread.interrupt();

        // to produce better output
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // no action is required.
        }

        System.out.println("End of main");
    }
}
