package threadsafe;

/**
 * Write a unit test to show this class is not thread-safe
 */
public class UnsafeCounter {

    private int count = 0;

    public int getCount() {
        return count;
    }

    public void increment() {
        this.count++;
    }

}
