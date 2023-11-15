package dev.fomenko;

public class CounterContainer {
    int counter = 0;
    int rawInt = 0;

    public void increment() {
        synchronized (this) {
            this.counter = counter + 1;
        }
    }

    public synchronized void increment2() {
        this.counter = counter + 1;
    }

    public int getCounter() {
        return counter;
    }
}