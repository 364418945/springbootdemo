package com.example.demo.guava;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class StopWatchTest {

    @Test
    public void oldMethod() throws InterruptedException {
        long begin = System.nanoTime();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(System.nanoTime() - begin);
    }

    @Test
    public void stopWatch() throws InterruptedException {
        long begin = System.nanoTime();
        Stopwatch stopwatch = Stopwatch.createStarted();
        TimeUnit.SECONDS.sleep(10);
        System.out.println(stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
//        TimeUnit.SECONDS.sleep(10);
//        System.out.println(stopwatch.s());
    }
}
