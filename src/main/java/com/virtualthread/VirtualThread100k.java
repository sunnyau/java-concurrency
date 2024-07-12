package com.virtualthread;

import java.util.ArrayList;
import java.util.List;

/**
 * Run 100k virtual threads.
 * 
 * In normal circumstance, It is not possible to run 100k platform threads.
 * 
 * Note : What is virtual thread ?
 * 
 * - new type of thread
 * - cheap to create
 * - recommand to run blocking code
 * - when blocked, it moves to heap memory
 * - not recommanded to run memory capulation.
 * - with spring boot
 */
public class VirtualThread100k {

    public static void main(String[] args) {

        int vThreadCount = 100_000;
        List<Thread> list = new ArrayList<>(vThreadCount);

        for (int i = 0; i < vThreadCount; i++) {
            final int taskNumber = i;
            Runnable task = () -> {
                System.out.println("task : " + taskNumber);
            };

            // start a virtual thread
            // Thread vThread = Thread.ofVirtual().start(task);

            Thread vThread = Thread.ofVirtual().unstarted(task);
            vThread.start();
            list.add(vThread);
        }

        // join them all and wait for all of them to finish.
        for (int i = 0; i < vThreadCount; i++) {
            try {
                list.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("End of main");

    }
}
