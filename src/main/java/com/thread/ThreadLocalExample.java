package com.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ThreadLocal is a class in Java that allows you to create variables that can
 * only be read and written by the same thread.
 * 
 * In this example, we use the 1 callable task in 10 threads. As the thread pool
 * size is only 4, there will be only up to 4 threads running at the same time.
 * 
 * What you see is that
 * - there are only 4 new object created for 4 different threads. ( i.e. one
 * object per thread. )
 * 
 * ThreadLocal is particularly useful for thread-unsafe object.
 * As there is no more than 1 thread to change this thread-unsafe, it becomes
 * thread safe.
 * 
 * Another way to use ThreadLocal is for Database Connection. ( To have one
 * database connection per thread instead of a new database connection per
 * connection. )
 */
public class ThreadLocalExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ThreadLocal<Object> threadLocal = new ThreadLocal<>();

        Callable<Object> task = () -> {
            Object value = threadLocal.get();
            if (value == null) {
                value = new Object();
                System.err.println("new object is created in " + Thread.currentThread().getName());
                threadLocal.set(value);
            }
            return value;
        };

        ExecutorService es = Executors.newFixedThreadPool(4);
        Future<Object> future1 = es.submit(task);
        Future<Object> future2 = es.submit(task);
        Future<Object> future3 = es.submit(task);
        Future<Object> future4 = es.submit(task);
        Future<Object> future5 = es.submit(task);
        Future<Object> future6 = es.submit(task);
        Future<Object> future7 = es.submit(task);
        Future<Object> future8 = es.submit(task);
        Future<Object> future9 = es.submit(task);
        Future<Object> future10 = es.submit(task);

        es.shutdown();

        System.out.println("ThreadLocal value in future1 thread: " + future1.get());
        System.out.println("ThreadLocal value in future2 thread: " + future2.get());
        System.out.println("ThreadLocal value in future3 thread: " + future3.get());
        System.out.println("ThreadLocal value in future4 thread: " + future4.get());
        System.out.println("ThreadLocal value in future5 thread: " + future5.get());
        System.out.println("ThreadLocal value in future6 thread: " + future6.get());
        System.out.println("ThreadLocal value in future7 thread: " + future7.get());
        System.out.println("ThreadLocal value in future8 thread: " + future8.get());
        System.out.println("ThreadLocal value in future9 thread: " + future9.get());
        System.out.println("ThreadLocal value in future10 thread: " + future10.get());

        System.out.println("End of main");

    }
}