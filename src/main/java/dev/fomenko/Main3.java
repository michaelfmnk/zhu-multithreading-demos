package dev.fomenko;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main3 {

    public static class CommonData {
        public int counter = 0;
        
        public synchronized void increment() {
            this.counter = counter + 1;
        }
        
        public void inc() {
            synchronized (this) {
                this.counter = counter + 1;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        var commonData = new CommonData();

        var atomicInteger = new AtomicInteger();

        var fixedThreadPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            fixedThreadPool.submit(() -> {
                System.err.println("Thread " + Thread.currentThread().getName() + " started");
                for (int j = 0; j < 100_000; j++) {
                    commonData.increment();
                    atomicInteger.incrementAndGet();
                }
            });
        }

        fixedThreadPool.shutdown();
        fixedThreadPool.awaitTermination(100_000, java.util.concurrent.TimeUnit.SECONDS);

        System.err.println("Counter value: " + commonData.counter);
        System.err.println("AtomicInteger value: " + atomicInteger.get());
    }


}