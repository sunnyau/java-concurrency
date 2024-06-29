package buildingblock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RunnableLambda2 {

    private Lock lock = new ReentrantLock();        
    // int counter = 0;
    private final AtomicInteger counter = new AtomicInteger(0);

    public void runMain() {
        for ( int i = 0 ; i < 10 ; i++ ) {
            Runnable runnable = () -> runnableLambdaBody();
            Thread thread = new Thread( runnable );
            thread.start();
        }
    }

    private void runnableLambdaBody() {
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

    public static void main(String[] args) {
        RunnableLambda2 runnableLambda2 = new RunnableLambda2();
        runnableLambda2.runMain();
    }
}
