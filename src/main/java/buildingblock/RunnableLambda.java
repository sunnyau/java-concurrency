package buildingblock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Note : counter (int) cannot be used. 
 * Local variable counter defined in an enclosing scope must be final or effectively final
 */
public class RunnableLambda {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();        
        // int counter = 0;
        final AtomicInteger counter = new AtomicInteger(0);

        for ( int i = 0 ; i < 10 ; i++ ) {
            Runnable runnable = () -> runnableLambdaBody( lock, counter );
            Thread thread = new Thread( runnable );
            thread.start();
        }
    }

    private static void runnableLambdaBody( Lock lock, AtomicInteger counter ) {
        {
            lock.lock();
            try {                    
                // Note : counter cannot be used. 
                // Local variable counter defined in an enclosing scope must be final or effectively final
                // counter++;
                counter.incrementAndGet();
                System.out.println(Thread.currentThread().getName() + " --> " + counter.get() );        
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                lock.unlock();
            }
        }
    }
}