package threadsafety;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Write a unit test to show this class is NOT thread-safe
 */
public class UnsafeCounter {

    private int count = 0;

    public int getCount() {
        return count;
    }

    public void incrementMillionTimes() {
        for ( int i = 0 ; i < 1_000_000 ; i++ ) {
            count++;
        }        
    }

}
