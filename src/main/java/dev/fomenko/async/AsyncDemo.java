package dev.fomenko.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AsyncDemo {

    public void futureDemo() throws ExecutionException, InterruptedException {
        ExecutorService threadpool = Executors.newCachedThreadPool();
        Future<Long> futureTask = threadpool.submit(() -> {
            // Simulate a long-running task
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return 123L;
        });

        while (!futureTask.isDone()) {
            System.out.println("FutureTask is not finished yet...");
        }
        long result = futureTask.get();

        threadpool.shutdown();
    }

    public void simpleCompletableFutureDemo() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // Simulate a long-running task
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Running asynchronously: " + Thread.currentThread().getName());
            return 123;
        });
        Integer result = future.get();// waits for the task to complete
        System.err.println("Result: " + result);
    }

    public void applyingAsyncOperations() throws ExecutionException, InterruptedException {
        CompletableFuture<String> welcomeText = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // task 1
            return "Hello";
        }).thenApply(name -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // task 2 (after 1)
            return name + ", World!";
        });
        System.out.println(welcomeText.get());
    }

    public void combiningCompletableFutures() throws ExecutionException, InterruptedException {
        // task 1 and 2 are executed in parallel
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Hello";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "World";
        });


        CompletableFuture<String> combinedFuture = future1.thenCombine(future2,
                // task 3. This task is executed after both tasks 1 and 2 are completed
                (s1, s2) -> s1 + " " + s2
        );
        System.out.println(combinedFuture.get());
    }

    public void exceptionHandlingInFutures() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (Math.random() < 0.5) {
                throw new RuntimeException("Something went wrong");
            }
            return "Success";
        })
                .exceptionally(ex -> "Failed due to " + ex.getMessage());
        System.out.println(future.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AsyncDemo demo = new AsyncDemo();
        demo.simpleCompletableFutureDemo();
        demo.applyingAsyncOperations();
        demo.combiningCompletableFutures();
        demo.exceptionHandlingInFutures();
    }

}
