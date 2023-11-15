package dev.fomenko.sync;

import java.util.concurrent.Exchanger;

class FirstThread implements Runnable {
    private Exchanger<String> exchanger;

    public FirstThread(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            System.out.println("First thread is doing some processing...");
            Thread.sleep(2000); // Simulating some processing time
            String message = "Data from FirstThread";
            System.out.println("First thread has some data to exchange: " + message);

            // Exchanging data with the second thread
            String receivedMessage = exchanger.exchange(message);

            System.out.println("First thread received: " + receivedMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SecondThread implements Runnable {
    private Exchanger<String> exchanger;

    public SecondThread(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            System.out.println("Second thread is doing some processing...");
            Thread.sleep(10000); // Simulating some processing time
            String message = "Data from SecondThread";
            System.out.println("Second thread has some data to exchange: " + message);

            // Exchanging data with the first thread
            String receivedMessage = exchanger.exchange(message);

            System.out.println("Second thread received: " + receivedMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ExchangerDemo {
    public static void main(String[] args) {
        // Creating an Exchanger object with generic type String
        Exchanger<String> exchanger = new Exchanger<>();

        // Creating threads and passing the Exchanger object to them
        Thread firstThread = new Thread(new FirstThread(exchanger));
        Thread secondThread = new Thread(new SecondThread(exchanger));

        // Starting the threads
        firstThread.start();
        secondThread.start();
    }
}
