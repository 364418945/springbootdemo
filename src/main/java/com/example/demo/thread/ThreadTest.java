package com.example.demo.thread;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.TimeUnit;

/**
 * @author cityre
 * @create 2019-07-15
 * @desc threadtest
 **/
public class ThreadTest {

    public static void doS() {
        try {
            System.out.println(Thread.currentThread().getName()+"开始等待");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+"被唤醒");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                doS();
            }
        });

        a.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"叫醒你");
        a.interrupt();

    }
}
