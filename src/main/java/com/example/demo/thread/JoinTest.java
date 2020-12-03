package com.example.demo.thread;

public class JoinTest {


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"---------"+i);
                }
            }
        });



        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"=========="+i);
                }
            }
        });


        t1.start();
        t2.start();

        t1.join();
        t2.join();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(10);
            System.out.println(Thread.currentThread().getName()+">>>>>>>>>>>>>"+i);
        }
    }
}
