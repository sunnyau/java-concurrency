package buildingblock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Update with Java 8 Lambda 
 */
class ExecutorExample {
  public static void main(String args[]) {
    
    ExecutorService es = Executors.newFixedThreadPool(2);

    es.execute( () -> System.out.println("A"));
    es.execute( () -> System.out.println("B"));
    es.execute( () -> System.out.println("C"));
    es.execute( () -> System.out.println("D"));

    
    es.shutdown();
    System.out.println("End");
  }
}

// class ExecutorExampleMyThread implements Runnable {
//   String name;

//   ExecutorExampleMyThread(String n) {
//     name = n;
//   }

//   public void run() {
//   	  System.out.println(name);
//   }
// }
