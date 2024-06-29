package completablefuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

/**
 * This test get 
 * 
 * @author Sunny
 * 
 *
 */
public class CompletableFutureTest {

    /**
     * Single thread example.
     * 
     * We get the price from each online shop.
     */
	@Test
	public void testGetPriceFromShopOneByOne() throws InterruptedException {
		
		LocalDateTime startDt = LocalDateTime.now();
		Shop onlineShop = new Shop();
		
		double totalPrice = 0;
		totalPrice += onlineShop.getPrice("book");
		totalPrice += onlineShop.getPrice("phone");
		totalPrice += onlineShop.getPrice("battery");
		totalPrice += onlineShop.getPrice("pen");
		
		long msTaken = startDt.until( LocalDateTime.now(), ChronoUnit.MILLIS);
		System.out.println("testGetPriceFromShopOneByOne. Time taken [" + msTaken + "] ms." );
        assertEquals(833.0, totalPrice);
	}
	
	/**
	 * Although thread was introduced since Java was born, it was quite difficult to use.
	 * 
	 * In JDK 5. Future, ExecutorService and Callable interface are introduced to make run multiple threads easier.
	 * 
	 * Any class implementing Callable interface is a thread.
	 * ExecutorService is a thread pool.
	 * Future interface holds a value, which you can get in the future.
	 */
	@Test
	public void testGetPriceFromShopsAtTheSameTime() throws InterruptedException, ExecutionException {
		
		LocalDateTime startDt = LocalDateTime.now();
		Shop onlineShop = new Shop();
		
		// thread pool with 4 threads.
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		
		Future<Double> future1 = executorService.submit( () -> onlineShop.getPrice("book") );
		Future<Double> future2 = executorService.submit( () -> onlineShop.getPrice("phone") );
		Future<Double> future3 = executorService.submit( () -> onlineShop.getPrice("battery") );		
		Future<Double> future4 = executorService.submit( () -> onlineShop.getPrice("pen") );
		
		// no more submit.
		executorService.shutdown();
		
		double totalPrice = future1.get() + future2.get() + future3.get() + future4.get();		
		
		long msTaken = startDt.until( LocalDateTime.now(), ChronoUnit.MILLIS);
		System.out.println("testGetPriceFromShopsAtTheSameTime. Time taken [" + msTaken + "] ms" );
        assertEquals(833.0, totalPrice);
	}
	
	/**
	 * CompletableFuture
	 * 
	 * Instead of creating an ExecutorService and passing in Callable, you can just use CompletableFuture.supplyAsync() instead.
	 * Note : You need to change the jre to 1.8
	 */
	@Test
	public void testGetPriceFromShopsUsingSupplyAsync() throws InterruptedException, ExecutionException {
		
		LocalDateTime startDt = LocalDateTime.now();
		Shop onlineShop = new Shop();
		 
		Future<Double> future1 = CompletableFuture.supplyAsync(() -> onlineShop.getPrice("book") );
		Future<Double> future2 = CompletableFuture.supplyAsync(() -> onlineShop.getPrice("phone"));
 		Future<Double> future3 = CompletableFuture.supplyAsync(() -> onlineShop.getPrice("battery"));		
		Future<Double> future4 = CompletableFuture.supplyAsync(() -> onlineShop.getPrice("pen"));
		
		double totalPrice = future1.get() + future2.get() + future3.get() + future4.get();		
		
		long msTaken = startDt.until( LocalDateTime.now(), ChronoUnit.MILLIS);
		System.out.println("testGetPriceFromShopsUsingSupplyAsync. Time taken [" + msTaken + "] ms" );
        assertEquals(833.0, totalPrice);
	}
	
	
	
}
