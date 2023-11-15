package dev.fomenko;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Creating a CompletableFuture that will be completed with a result later
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        // Asynchronous task that completes the CompletableFuture after 2 seconds
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                completableFuture.complete("Hello, CompletableFuture!");
            } catch (InterruptedException e) {
                completableFuture.completeExceptionally(e);
            }
        }).start();

        // Waiting for the CompletableFuture to complete and printing the result
        System.out.println("Waiting for the CompletableFuture to complete...");
        String result = completableFuture.get(); // This blocks until the Future is complete
        System.out.println("Result: " + result);
        System.err.println("----------------------------------");

        // Using thenApply to perform operations on the CompletableFuture result asynchronously
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(s -> s + " World")
                .thenApply(String::toUpperCase);

        System.out.println("Chained CompletableFuture Result: " + future.get());

        System.err.println("----------------------------------");

        // Combining multiple CompletableFutures using thenCombine
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);

        CompletableFuture<Integer> combinedFuture = future1.thenCombine(future2, (x, y) -> x + y);
        System.out.println("Combined CompletableFuture Result: " + combinedFuture.get());

        // Handling errors with exceptionally
        CompletableFuture<Integer> exceptionallyFuture = CompletableFuture
                .supplyAsync(() -> {
                    // Simulating an error
                    if (true) throw new RuntimeException("Exception occurred!");
                    return -1;
                })
                .exceptionally(ex -> {
                    System.out.println("Error: " + ex.getMessage());
                    return 0;
                });

        System.out.println("Exceptionally CompletableFuture Result: " + exceptionallyFuture.get());
    }
}
