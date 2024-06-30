package buildingblock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadCountAtomic {

	private AtomicInteger count = new AtomicInteger();
	//private Lock lock = new ReentrantLock();

	public int getCount() {
		return count.get() ;
	}
	/**
	 * Use AtomicInteger
	 * @throws InterruptedException
	 */
	public void increment() throws InterruptedException {
			Thread.sleep(1);
			count.getAndIncrement() ;
	}	

	
	
	public static void main( String[] args ) {
		ThreadCountAtomic threadCount = new ThreadCountAtomic() ;
		
		ExecutorService taskExecutor = Executors.newFixedThreadPool(3);	
		for ( int i = 0 ; i < 10000 ; i++ ) {
			taskExecutor.submit(new CallableTaskAtomic( threadCount ));
		}
		taskExecutor.shutdown();
		
		try {
			  taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			  //
		}

		
		
		System.out.println( threadCount.getCount()) ;
		
	}
}

class CallableTaskAtomic implements Callable<Object> {

	private ThreadCountAtomic threadCount;
	
	public CallableTaskAtomic( ThreadCountAtomic threadCount ) {
		this.threadCount = threadCount;
	}
	
	public Object call() throws Exception {
		threadCount.increment();
		return null;
	}
	
}

