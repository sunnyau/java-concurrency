package com.thread;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.threadsafety.UnsafeCounter;

/* ThreadLocal is a class in Java that allows you to create variables that can
 * only be read and written by the same thread.
 */
public class ThreadLocalTest {

    /*
     * What you see is that
     * - there are only 4 new object created for 4 different threads. ( i.e. one
     * object per thread. )
     * 
     * ThreadLocal is particularly useful for thread-unsafe object (i.e.
     * UnsafeCounter).
     * As there is no more than 1 thread to change this thread-unsafe, it becomes
     * thread safe.
     */
    @Test
    public void eachThreadCreateOneObject() {

        final int THREAD_POOL_SIZE = 4;
        final int NO_OF_THREADS = 100;
        ThreadLocal<UnsafeCounter> threadLocal = new ThreadLocal<>();

        Callable<UnsafeCounter> task = () -> {
            UnsafeCounter unsafeCounter = threadLocal.get();
            if (unsafeCounter == null) {
                unsafeCounter = new UnsafeCounter();
                System.out.println("new UnsafeCounter is created in " + Thread.currentThread().getName());
                threadLocal.set(unsafeCounter);
            }
            // we increment the unsafeCounter
            unsafeCounter.increment();
            return unsafeCounter;
        };

        ExecutorService es = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        Set<Future<UnsafeCounter>> futures = new HashSet<>();

        // fancy way to write a for loop to submit a task
        IntStream.range(0, NO_OF_THREADS).forEach(i -> futures.add(es.submit(task)));

        // no more new tasks for submit.
        es.shutdown();

        // we put the UnsafeCounter object into a set so that we can assert it
        Set<UnsafeCounter> unsafeCounterSet = futures.stream().map(f -> futureGet(f)).collect(Collectors.toSet());

        // Only 4 objects are created. Each thread creates one object.
        assertEquals(THREAD_POOL_SIZE, unsafeCounterSet.size());

        // As the total of all unsafeCounters is equal to the no of thread, it means
        // there is no race condition even when an unsafeCounter is used.
        assertEquals(NO_OF_THREADS, unsafeCounterSet.stream().mapToInt(counter -> counter.getCount()).sum());

    }

    private UnsafeCounter futureGet(Future<UnsafeCounter> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    /**
     * We use the same ThreadLocal variable. We set it to 42 in main. But the task
     * threadLocal.get() will return 0.
     */
    @Test
    public void threadLocalInMainHoldsItsValue() throws InterruptedException, ExecutionException {

        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
        threadLocal.set(42);

        Callable<Integer> task = () -> {
            int value = threadLocal.get();
            System.out.println("ThreadLocal value in new thread: " + value);
            return value;
        };

        ExecutorService es = Executors.newCachedThreadPool();
        Future<Integer> future = es.submit(task);
        es.shutdown();

        // the main returns 42
        assertEquals(42, threadLocal.get());

        // the task returns 0
        assertEquals(0, future.get());
    }

}
