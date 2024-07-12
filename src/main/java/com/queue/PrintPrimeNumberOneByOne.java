package com.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class
 * - running a thread to add prime number into a blocking queue
 * - at the same time, we take prime number out of the queue one by one.
 * 
 * The key information is that when we are taking a number out from the queue,
 * the thread is still adding numbers into the queue.
 * 
 */
public class PrintPrimeNumberOneByOne {

    private static final int PRIME_NUMBER_BELOW_THIS_UPPER_BOUND = 100;

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(5);
        Thread putThread = putPrimeNumbersIntoQueue(queue, PRIME_NUMBER_BELOW_THIS_UPPER_BOUND);
        printPrimeNumberOnebyOne(queue, putThread);
    }

    private static Thread putPrimeNumbersIntoQueue(BlockingQueue<Integer> queue, int upperBound)
            throws InterruptedException {
        Runnable producer = () -> {
            for (int i = 2; i < upperBound; i++) {
                if (isPrime(i)) {
                    try {
                        queue.put(Integer.valueOf(i));
                    } catch (InterruptedException e) {
                        System.out.println("put is interrupted");
                    }
                }
            }
        };
        Thread thread = new Thread(producer);
        thread.start();
        return thread;
    }

    /**
     * It keeps printing until there is nothing in the queue.
     */
    private static void printPrimeNumberOnebyOne(BlockingQueue<Integer> queue, Thread putThread)
            throws InterruptedException {
        Thread.sleep(200);
        while (queue.size() > 0) {
            Thread.sleep(200);
            System.out.println(queue.take() + ". Is putThread still alive ? " + putThread.isAlive() + ". Queue size is "
                    + queue.size());
        }
    }

    private static boolean isPrime(int num) {
        boolean prime = true;
        int limit = (int) Math.sqrt(num);
        for (int i = 2; i <= limit; i++) {
            if (num % i == 0) {
                prime = false;
                break;
            }
        }
        return prime;
    }
}
