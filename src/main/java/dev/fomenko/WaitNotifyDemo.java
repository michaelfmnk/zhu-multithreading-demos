package dev.fomenko;

public class WaitNotifyDemo {


    public static class Mailbox {
        volatile private String message;
        volatile private boolean isEmpty = true;

        public synchronized void put(String message) {
            this.message = message;
            isEmpty = false;
        }

        public synchronized String get() {
            System.err.println("Message " + message);
            isEmpty = true;
            return message;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        var mailbox = new Mailbox();

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.err.println("Producing message " + i);
                mailbox.put("message" + i);
            }
        });


        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.err.println("Consuming message " + i);
                System.err.println(mailbox.get());
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}

class WaitNotifyDemo3 {


    public static class Mailbox {
        private String message;
        private boolean isEmpty = true;


        public synchronized void put(String message) {
            while (!isEmpty) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.message = message;
            isEmpty = false;
            notify();
        }


        public synchronized String get() {
            while (isEmpty) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            isEmpty = true;
            String result = message;
            notify();
            return result;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        var mailbox = new Mailbox();


        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                mailbox.put("message" + i);
            }
        });


        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.err.println(mailbox.get());
            }
        });


        producer.start();
        consumer.start();


        producer.join();
        consumer.join();
    }
}
