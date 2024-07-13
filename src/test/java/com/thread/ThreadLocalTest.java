package com.thread;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

/**
 * We use the same ThreadLocal variable. We set it to 42 in main. But the task
 * threadLocal.get() will return 0.
 */
public class ThreadLocalTest {

    @Test
    public void threadLocalReturnsDifferentValues() throws InterruptedException, ExecutionException {

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

    @Test
    public void onlyFourObjectsAreCreated() {
        
    }

}
