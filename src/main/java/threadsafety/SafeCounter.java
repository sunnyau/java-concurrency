package threadsafety;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import annotation.ThreadSafe;

@ThreadSafe
public class SafeCounter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public void incrementMillionTimes() {
        lock.lock();
        try {
            for (int i = 0; i < 1_000_000; i++) {
                count++;
            }
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}
