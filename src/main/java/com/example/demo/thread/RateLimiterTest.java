package com.example.demo.thread;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RateLimiterTest {

    static ConcurrentHashMap<String,RateLimiter> concurrentHashMap = new ConcurrentHashMap();

    static {
        createResourceRateLimiter("order",1);//每秒50个令牌
    }

    public static void createResourceRateLimiter(String resource,double qps){
        if(concurrentHashMap.contains(resource)){
            concurrentHashMap.get(resource).setRate(qps);
        }else{
            RateLimiter rateLimiter = RateLimiter.create(qps,10,TimeUnit.SECONDS);//每条生成令牌数
            concurrentHashMap.putIfAbsent(resource,rateLimiter);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
//        TimeUnit.SECONDS.sleep(10);
        Thread t = null;
        List<Thread> threaList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {//模拟100个请求
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(concurrentHashMap.get("order").acquire());
//                    try {
//                        Thread.sleep(10);
//                        System.out.println("doSomething");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if(concurrentHashMap.get("order").tryAcquire()){
//
//                        System.out.println("do");
//                    }else{
//                        System.out.println("wait");
//                    }
                }
            });
            threaList.add(t);
            t.start();
        }
        try {
            for (Thread thread : threaList) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("all:"+stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));

    }
}
