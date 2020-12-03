package com.example.demo.thread;

import org.junit.Test;

public class ChongRuSuoTest {

    @Test
    public void testSync() {
        A a = new A();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                a.doS();
            }
        });


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                a.doS1();
            }
        });

        t.start();
        t1.start();
        try {
            t.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }





    public static class A{
        public synchronized void doS(){
            System.out.println(Thread.currentThread().getName()+"doS");
            doS1();
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        public synchronized void doS1(){
            System.out.println(Thread.currentThread().getName()+"doS1");
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
