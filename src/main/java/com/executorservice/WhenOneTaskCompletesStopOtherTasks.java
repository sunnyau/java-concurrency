package com.executorservice;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * We run multiple tasks. When one task has completed, the other tasks are
 * interrupted.
 */
public class WhenOneTaskCompletesStopOtherTasks {

    public static void main(String[] args) {

        MyTask task1 = new MyTask("task 1");
        MyTask task2 = new MyTask("task 2");
        MyTask task3 = new MyTask("task 3");

        ExecutorService ex = Executors.newCachedThreadPool();

        try {
            String result = ex.invokeAny(List.of(task1, task2, task3));
            System.out.println(result);
        } catch (InterruptedException e) {
            // ignore
        } catch (ExecutionException e) {
            // ignore
        }

        ex.shutdown();

        System.out.println("End of main");
    }
}

class MyTask implements Callable<String> {

    private final Random random = new Random();
    private String name;

    public MyTask(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        System.out.println(name + " is running");
        try {
            int time = random.nextInt(2000);
            Thread.sleep(time);
            return name + " won in " + time + " ms.";
        } catch (InterruptedException e) {
            System.out.println(name + " is interrupted");
            return "";
        }
    }

}
