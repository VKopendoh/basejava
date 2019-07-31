package com.vkopendoh.webapp;

public class MainDeadLock {
    private final static Object lock1 = new Object();
    private final static Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> lockOnO1thenO2(lock1, lock2));
        Thread thread2 = new Thread(() -> lockOnO1thenO2(lock2, lock1));
        thread1.start();
        thread2.start();
    }

    private static void lockOnO1thenO2(Object o1, Object o2) {
        synchronized (o1) {
            System.out.println(Thread.currentThread().getName() + ", locked at " + o1.toString() + " , try lock on " + o2.toString() + "...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2) {
                System.out.println(Thread.currentThread().getName() + ", locked at" + o1.toString());
            }
        }
    }
}