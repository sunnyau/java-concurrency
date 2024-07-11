package interruptthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * TODO 
 */
public class FutureGet {

        public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(2);

        Callable<String> callable = () -> {
            while(!Thread.currentThread().isInterrupted()) {
                System.out.println("thread is running");
                Thread.sleep(1000);
            }
            return "End of Thread";
        };

        Future<String> future = es.submit(callable);

        // accept no more task
        es.shutdown(); 

        // try {
        //     TimeUnit.SECONDS.sleep(5);
        // } catch (InterruptedException e) {
        //     // no action is required.
        // }

        System.out.println("Action : future.get( 5, TimeUnit.SECONDS)");

        try {
            future.get( 5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // As future is cancelled, calling future.get() will throw CancellationException

        // try {
        //     System.out.println(future.get());
        // } catch (InterruptedException | ExecutionException | CancellationException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        System.out.println("End of main");
    }
}
