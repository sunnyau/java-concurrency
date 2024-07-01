package buildingblock;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * We have a blockingQueue with the bound of 100.
 * Our producer adds a random String to the queue.
 * Our comsumer takes element from the queue.
 * 
 * As our producer adds element to the queue QUICKER than what our comsumer comsumes, 
 * you will see the size of the queue increases gradually until it reachs its bounds = 100.
 * 
 */
public class BlockingQueueExampleLambda {

	private static final int BOUND = 100;
	public static void main(String[] args) throws InterruptedException {

		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(BOUND);

		// Producer producer = new Producer(queue);
		// Consumer consumer1 = new Consumer(queue);
		// Consumer consumer2 = new Consumer(queue);

		Runnable producer = () -> {
			try {
				while (true) {
					Thread.sleep(50);
					queue.put(new String());
				}
			} catch (InterruptedException ex) {
			}
		};

		Runnable consumer1 = () -> {
			try {
				while (true) {
					Thread.sleep(2000);
					queue.take();
				}
			} catch (InterruptedException ex) {
			}
		};

		Runnable consumer2 = () -> {
			try {
				while (true) {
					Thread.sleep(2000);
					queue.take();
				}
			} catch (InterruptedException ex) {
			}
		};

		new Thread(producer).start();
		new Thread(consumer1).start();
		new Thread(consumer2).start();

		while (true) {
			Thread.sleep(1000);
			System.out.println("The size of the queue is " + queue.size());
		}
		
	}
}
