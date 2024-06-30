package buildingblock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadCount {

	private int count;
	private Lock lock = new ReentrantLock();

	public int getCount() {
		return count ;
	}
	/**
	 * Use synchronized keyword in method
	 * @throws InterruptedException
	 */
	public synchronized void increment1() throws InterruptedException {
		synchronized( this ) {
			Thread.sleep(1);
			count++ ;
		}
	}	
	/**
	 * Use synchronized block
	 * @throws InterruptedException
	 */
	public void increment2() throws InterruptedException {
		synchronized( this ) {
			Thread.sleep(1);
			count++ ;
		}
	}
	/**
	 * Use Reentrantlock
	 * @throws InterruptedException
	 */
	public  void increment3() throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1);
			count++ ;			
		}
		finally {
			lock.unlock();
		}
	}

	public static void main( String[] args ) {
		ThreadCount threadCount = new ThreadCount() ;
		
		ExecutorService taskExecutor = Executors.newFixedThreadPool(3);	
		for ( int i = 0 ; i < 10000 ; i++ ) {
			taskExecutor.submit(new CallableTask( threadCount ));
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

class CallableTask implements Callable<Object> {

	private ThreadCount threadCount;
	
	public CallableTask( ThreadCount threadCount ) {
		this.threadCount = threadCount;
	}
	
	public Object call() throws Exception {
		threadCount.increment3();
		return null;
	}
	
}

