package buildingblock;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Producer implements Runnable {
	private final BlockingQueue<Object> queue;

	Producer(BlockingQueue<Object> q) {
		queue = q;
	}

	public void run() {
		try {
			while (true) {
				queue.put(produce());
			}
		} catch (InterruptedException ex) {
		}
	}

	public Object produce() {
		return null;
	}
}

class Consumer implements Runnable {
	private final BlockingQueue<Object> queue;

	Consumer(BlockingQueue<Object> q) {
		queue = q;
	}

	public void run() {
		try {
			while (true) {
				consume(queue.take());
			}
		} catch (InterruptedException ex) {
		}
	}

	public void consume(Object x) {
	}
}

public class BlockingQueueExample {
	void main() {
		// BlockingQueue q = new SomeQueueImplementation();
		BlockingQueue<Object> q = new LinkedBlockingQueue<Object>();
		Producer p = new Producer(q);
		Consumer c1 = new Consumer(q);
		Consumer c2 = new Consumer(q);
		new Thread(p).start();
		new Thread(c1).start();
		new Thread(c2).start();
	}
}
