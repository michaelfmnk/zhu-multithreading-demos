package dev.fomenko.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolDemo {

    public static void main(String[] args) {
        // Create a fixed-size thread pool with 5 threads
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Submit tasks to the thread pool
        for (int i = 1; i <= 10; i++) {
            Task task = new Task("Task " + i);
            executor.submit(task);
        }

        // Shutdown the executor after all tasks are submitted
        executor.shutdown();
    }

    static class Task implements Runnable {
        private String taskName;

        public Task(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            System.out.println("Executing " + taskName + " on thread " + Thread.currentThread().getName());
            // Simulate some task execution time
            try {
                Thread.sleep(2000); // Sleep for 2 seconds to simulate task execution
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(taskName + " completed on thread " + Thread.currentThread().getName());
        }
    }
}

