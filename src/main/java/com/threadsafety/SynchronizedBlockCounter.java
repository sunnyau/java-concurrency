package com.threadsafety;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Use synchronized block in the method
 */
public class SynchronizedBlockCounter {

	private int count;

	public int getCount() {
		synchronized (this) {
			return count;
		}
	}

	public void increment() throws InterruptedException {
		synchronized (this) {
			count++;
		}
	}

	public static void main(String[] args) {
		SynchronizedBlockCounter threadCount = new SynchronizedBlockCounter();

		ExecutorService taskExecutor = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10000; i++) {
			Callable<Object> callable = () -> {
				threadCount.increment();
				return null;
			};
			taskExecutor.submit(callable);
		}
		taskExecutor.shutdown();

		try {
			taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			//
		}

		System.out.println(threadCount.getCount());

	}
}
