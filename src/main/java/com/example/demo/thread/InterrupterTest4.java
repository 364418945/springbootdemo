package com.example.demo.thread;

public class InterrupterTest4 {
    public static void main(String[] args) {

        Thread mt = Thread.currentThread();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
//                    try {
//                        Thread.sleep(1000);
//
//                    } catch (InterruptedException e) {
//                        //InterruptedException将状态还原所以显示（false）
//                        System.out.println(Thread.currentThread().getName()+ ":"+ Thread.interrupted() + " sleep");
//                        e.printStackTrace();
//                    }
                    try {
                        System.out.println(Thread.currentThread().getName()+"-------------"+i+"等待main");
                        mt.join();
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName()+ ":"+ Thread.interrupted() +" join");
                        e.printStackTrace();
                    }

//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });

        t1.start();
        //main线程中断t1
//        t1.interrupt();
        //t1是否中断(true)
//        System.out.println(t1.isInterrupted());

        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(Thread.currentThread().getName()+">>>>>>>>>>>>>>>>>>"+i);
                System.out.println("main do something...");
                Thread.sleep(1000);
                t1.interrupt();
                System.out.println(123);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }






    }
}
