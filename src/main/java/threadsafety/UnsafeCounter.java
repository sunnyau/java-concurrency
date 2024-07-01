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

    public void increment() {
        count++;
    }

    // private ReentrantLock lock = new ReentrantLock();

    // public int getCount() {
    // lock.lock();
    // try {
    // return count;
    // } catch (Exception e) {
    // return Integer.MIN_VALUE;
    // } finally {
    // lock.unlock();
    // }
    // }

    // public void increment() {
    // lock.lock();
    // try {
    // this.count++;
    // } catch (Exception e) {
    // } finally {
    // lock.unlock();
    // }
    // }
}
