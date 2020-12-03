package com.example.demo.thread;

import javax.sound.midi.Soundbank;

public class waitTest {
    private static volatile boolean produce = false;
    private static int i = 0;

    public static void main(String[] args) {
        Object lock = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (lock) {
                        if (!produce) {
                            lock.notify();
                            produce = true;
                            System.out.println("produce " + ++i);
                        } else {
                            try {
                                System.out.println("等待消费");
                                lock.wait();
                                System.out.println("after wait");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (lock) {

                        if (produce) {
                            lock.notify();
                            produce = false;
                            System.out.println("cosumser" + i);
                        } else {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }
}
