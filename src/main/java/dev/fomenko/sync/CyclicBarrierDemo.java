package dev.fomenko.sync;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Worker implements Runnable {
    private CyclicBarrier barrier;
    private String name;

    public Worker(CyclicBarrier barrier, String name) {
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " is working");
        try {
            // Simulating some work by sleeping for a random duration
            Thread.sleep((long) (Math.random() * 3000));
            System.out.println(name + " is done with work and waiting at the barrier");
            // Wait at the barrier
            barrier.await();
            System.out.println(name + " has crossed the barrier");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        int numberOfWorkers = 3;
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            System.out.println("All workers have reached the barrier, let's continue...");
        });

        for (int i = 1; i <= numberOfWorkers; i++) {
            Thread workerThread = new Thread(new Worker(barrier, "Worker " + i));
            workerThread.start();
        }
    }
}

