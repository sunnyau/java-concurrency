package buildingblock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ImplementRunnable implements Runnable {

    private Lock lock = new ReentrantLock();
    private static int counter = 0;

    @Override
    public void run() {
        lock.lock();
        try {
            counter++;
            System.out.println(Thread.currentThread().getName() + " --> " + counter);            
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        for ( int i = 0 ; i < 10 ; i++ ) {
            Thread thread = new Thread( new ImplementRunnable() );
            thread.start();
        }
    }


}
