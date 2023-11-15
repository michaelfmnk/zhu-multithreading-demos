package dev.fomenko;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // 1st thread
        Thread thread = new MyTask();

        // 2nd thread
        Runnable runnable = new MyTask2();
        Thread thread2 = new Thread(runnable);

        // 3rd thread
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.err.println("Hello, world from Lambda " + i);
            }
        });

        // start both
        thread.start();
        thread2.start();
        thread3.start();

        for (int i = 0; i < 100; i++) {
            System.err.println("Hello, world from main " + i);
        }

        // wait to finish
        thread.join();
        thread2.join();
        thread3.join();
    }

    public static class MyTask extends Thread {
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.err.println("Hello, world from MyTask Thread " + i);
            }
        }
    }

    public static class MyTask2 implements Runnable {
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.err.println("Hello, world from MyTask Runnable " + i);
            }
        }
    }
}