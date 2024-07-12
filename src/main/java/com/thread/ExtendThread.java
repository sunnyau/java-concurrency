package com.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExtendThread extends Thread {

    private Lock lock = new ReentrantLock();
    private static int counter = 0;

    @Override
    public void run() {
        lock.lock();
        try {
            counter++;
            System.out.println(Thread.currentThread().getName() + " --> " + counter);            
        } catch (Exception e) {
            // handle exception
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        for ( int i = 0 ; i < 10 ; i++ ) {
            ExtendThread thread = new ExtendThread();
            thread.start();
        }

    }
}
