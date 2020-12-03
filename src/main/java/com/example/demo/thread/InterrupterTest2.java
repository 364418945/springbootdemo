package com.example.demo.thread;

public class InterrupterTest2 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+">>>>>>>>>>>>>>>>>>"+i);
        }

        System.out.println(Thread.interrupted());
        Thread tm = Thread.currentThread();
        tm.interrupt();//打断动作（打断当前线程）
        System.out.println(tm.isInterrupted());//不会清除打断状态
        System.out.println(Thread.interrupted());//会清除（从true--->false）
        System.out.println(Thread.interrupted());
    }
}
