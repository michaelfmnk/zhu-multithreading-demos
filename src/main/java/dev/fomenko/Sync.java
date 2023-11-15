package dev.fomenko;

import java.util.concurrent.atomic.AtomicInteger;

public class Sync {

    public static void main(String[] args) throws InterruptedException {
        var container = new CounterContainer();

        AtomicInteger atomicInteger = new AtomicInteger();

        var thread1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                container.rawInt = container.rawInt + 1;
                container.increment();
                atomicInteger.incrementAndGet();
            }
        });
        var thread2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                container.rawInt = container.rawInt + 1;
                container.increment();
                atomicInteger.incrementAndGet();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Counter value: " + container.getCounter());
        System.out.println("AtomicInteger value: " + atomicInteger.get());
        System.out.println("Raw int value: " + container.rawInt);
    }

    // explanation:
    // 1. thread1 starts
    // 2. thread2 starts
    // 3. thread1 reads counter value (0)
    // 4. thread2 reads counter value (0)
    // 5. thread1 increments counter (1)
    // 6. thread2 increments counter (1)
    // 7. thread1 writes counter value (1)
    // 8. thread2 writes counter value (1)

}
