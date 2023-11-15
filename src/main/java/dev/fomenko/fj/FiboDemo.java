package dev.fomenko.fj;

public class FiboDemo {

    public static void main(String[] args) {
        System.err.println(fibo(30));
    }

    public static int fibo(int n) {
        if (n <= 1) {
            return n;
        }

        return fibo(n - 1) + fibo(n - 2);
    }

    // 1 1 2 3 5
}
