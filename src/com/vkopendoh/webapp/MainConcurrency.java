package com.vkopendoh.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK = new Object();


    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            System.out.println("Line 2");
        }).start();
        System.out.println(thread.getState());
        MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            }).start();
            threads.add(thread);
        }
        //Thread.sleep(500);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);
    }

    private void inc() {
        double a = Math.sin(13.);
        synchronized (this) {
            try {
                counter++;
                wait(); // передать с этого места управление другому потоку
               // notify(); //закончить все что ниже и пробудить другой поток или все notifyAll()
                //readfile
                //...
           } catch (InterruptedException e) {
                e.printStackTrace();
           }
        }
    }
}
