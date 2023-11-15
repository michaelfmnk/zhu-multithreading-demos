package dev.fomenko.fj;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class FibonacciTask extends RecursiveTask<Integer> {
    private final int n;

    public FibonacciTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }

        FibonacciTask fib1 = new FibonacciTask(n - 1);
        FibonacciTask fib2 = new FibonacciTask(n - 2);
        fib1.fork();

        return fib2.compute() + fib1.join();
    }
}

public class FibonacciWithForkJoin {
    public static void main(String[] args) {
        int n = 20; // Specify the desired Fibonacci number to compute

        // Create a ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // Create a task to compute the Fibonacci number
        FibonacciTask fibonacciTask = new FibonacciTask(n);

        // Invoke the task within the ForkJoinPool
        int result = forkJoinPool.invoke(fibonacciTask);

        // Print the result
        System.out.println("Fibonacci number at index " + n + ": " + result);
    }
}
