package com.example.demo.thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentranLockTest {
    ReentrantLock reentrantLock = new ReentrantLock(true);


    @Test
    public void testSync() throws InterruptedException {
        Thread a = new Thread(new Job());
        Thread b = new Thread(new Job());
        Thread c = new Thread(new Job());
        Thread d = new Thread(new Job());
        a.start();
        b.start();
        c.start();
        d.start();
        a.join();
        b.join();
        c.join();
        d.join();


    }



    public class Job implements Runnable{

        @Override
        public void run() {

            doS();
        }
    }

    public void doS(){
        for (int i = 0; i < 2; i++) {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName()+"==="+i);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reentrantLock.unlock();
        }
    }
}
