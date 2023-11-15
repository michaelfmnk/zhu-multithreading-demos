package dev.fomenko.sync;

import java.util.concurrent.Semaphore;

class SharedResource {
    private static final int MAX_PERMITS = 3;
    private final Semaphore semaphore = new Semaphore(1);

    public void accessSharedResource(int threadId) {
        try {
            semaphore.acquire();
            System.err.println("Thread " + threadId + " is accessing the shared resource.");
            Thread.sleep(2000); // Simulating some task being performed
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.err.println("Thread " + threadId + " released the shared resource.");
            semaphore.release();
        }
    }
}

class WorkerThread extends Thread {
    private SharedResource sharedResource;
    private int threadId;

    public WorkerThread(SharedResource sharedResource, int threadId) {
        this.sharedResource = sharedResource;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        sharedResource.accessSharedResource(threadId);
    }
}

public class SemaphoreDemo {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        for (int i = 1; i <= 5; i++) {
            WorkerThread workerThread = new WorkerThread(sharedResource, i);
            workerThread.start();
        }
    }
}
