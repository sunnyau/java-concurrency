package buildingblock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ExecutorExample {
  public static void main(String args[]) {
    
    ExecutorService es = Executors.newFixedThreadPool(2);

    es.execute(new ExecutorExampleMyThread("A"));
    es.execute(new ExecutorExampleMyThread("B"));
    es.execute(new ExecutorExampleMyThread("C"));
    es.execute(new ExecutorExampleMyThread("D"));

    
    es.shutdown();
    System.out.println("End");
  }
}

class ExecutorExampleMyThread implements Runnable {
  String name;

  ExecutorExampleMyThread(String n) {
    name = n;
  }

  public void run() {
  	  System.out.println(name);
  }
}
