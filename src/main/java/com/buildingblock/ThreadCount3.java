package com.buildingblock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Use ReentrantLock
 */
public class ThreadCount3 {

	private int count;
	private Lock lock = new ReentrantLock();

	public int getCount() {
		lock.lock();
		try {
			return count;
		} finally {
			lock.unlock();
		}
	}

	public void increment() throws InterruptedException {
		lock.lock();
		try {
			Thread.sleep(1);
			count++;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		ThreadCount3 threadCount = new ThreadCount3();

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
