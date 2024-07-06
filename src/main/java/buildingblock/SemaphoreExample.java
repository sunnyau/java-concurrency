package buildingblock;

import java.util.concurrent.Semaphore;

/**
 * Semaphore is a counter that only allows how many permits to run at one time.
 * 
 * In this example, we allow two threads to run at one time.
 * 
 * https://www.baeldung.com/java-semaphore
 */
public class SemaphoreExample {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2, true);

        for ( int i = 0 ; i < 10 ; i++ ) {
            Runnable runnable = () -> {
                String threadName = Thread.currentThread().getName();
                try {
                    System.out.println( threadName + " is waiting for semaphore to acquire. " + semaphore.availablePermits() + " is available.");
                    semaphore.acquire();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                    System.out.println( threadName + " semaphore has released. " + semaphore.availablePermits() + " is available.");
                }
            };
            new Thread(runnable).start();
        }
    }

}
