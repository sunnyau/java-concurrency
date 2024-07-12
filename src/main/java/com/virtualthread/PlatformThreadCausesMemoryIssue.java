package com.virtualthread;

/**
 * Running platform thread with blocked task causes memory issue.
 * 
 * Note : You will notice the output become slower and slower after a few hundreds.
 */
public class PlatformThreadCausesMemoryIssue {

    public static void main(String[] args) {

        Runnable blockedTask = () -> {
            while (true) {
                // do nothing but blocked the task
            }
        };

        final int numberOfThreads = 100_000;

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = Thread.ofPlatform().unstarted(blockedTask);
            thread.start();
            String str = String.format("Java 21 thread number %s is running.", i);
            System.out.println(str);
        }

        // we should put all the threads into a list and then use join() to wait for them to finish.

        System.out.println("End of main");
    }
}
