package dev.fomenko.sync;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static class Task implements Runnable {
        private CountDownLatch latch;
        public Task(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            System.err.println("waiting for latch");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("latch released");
        }
    }


    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 5; i++) {
            Thread task = new Thread(new Task(latch));
            task.start();
        }
        try {
            Thread.sleep(5000);
            System.err.println("releasing latch");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }
}
