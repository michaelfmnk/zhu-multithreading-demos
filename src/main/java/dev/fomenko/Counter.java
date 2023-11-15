package dev.fomenko;

class Counter {
    public int count = 0;

    public synchronized void increment() {
//        while (count >= 1) {
            try {
                System.err.println("Waiting...");
                wait(); // Wait if the count is already incremented
            } catch (InterruptedException e) {
                System.err.println("Interrupted!");
                Thread.currentThread().interrupt();
            }
//        }
        count++;
        System.out.println("Incremented: " + count);
        notify(); // Notify waiting threads that increment operation is done
    }

}

class MainDemo {
    public static void main(String[] args) {
        Counter counter = new Counter();

        // Thread to increment
        Thread th1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                counter.increment();
            }
        });

        Thread th2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                counter.increment();
            }
        });

        th1.start();
        th2.start();

        synchronized (counter) {
            counter.notify();
        }

        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Count: " + counter.count);
    }
}
