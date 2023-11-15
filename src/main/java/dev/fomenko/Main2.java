package dev.fomenko;

import java.io.IOException;

public class Main2 {

    public static void main(String[] args) throws InterruptedException {
        Thread task = new MyTask();
        Thread task2 = new AntherTask();
        Thread task3 = new Thread(new ImplRunnableTask());
        Thread task4 = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }



            for (int i = 0; i < 100; i++) {
                System.err.println("TASK4 Hello, world from ImplRunnableTaskLambda Thread " + i);
            }
        });


        task4.start();
        task4.join(100_000);

        task3.start();



    }


    public static void method() throws IOException {
        throw new IOException();
    }

    static class ImplRunnableTask implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.err.println("Hello, world from ImplRunnableTask Thread " + i);
            }
        }
    }


    static class MyTask extends Thread {
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.err.println("Hello, world from MyTask Thread " + i);
            }
        }
    }

    static class AntherTask extends Thread {
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.err.println("Hello, world from AntherTask Thread " + i);
            }
        }
    }


}
