package com.example.demo.thread;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import net.bytebuddy.description.modifier.SynchronizationState;

import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        singleThread();
//
//        System.out.println("---------------------------------------------");
//        AllThread();

        ListenAbleFuture();

    }


    /**
     * 1个线程执行10次任务
     */
    public static void singleThread() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        long begin = System.currentTimeMillis();

        List<String> fl = Lists.newArrayList();
        Future<List> future = executorService.submit(new Callable<List>() {
            @Override
            public List call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    if(i/2==0){
                        Thread.sleep(2000);
                    }else{
                        Thread.sleep(1000);
                    }
                    String cuName = Thread.currentThread().getName()+"-----"+i;
                    fl.add(cuName);
                }
                return fl;
            }
        });

        List<String> r = future.get();
        System.out.println(r);
        System.out.println("耗时:" + (System.currentTimeMillis() - begin));
        executorService.shutdown();
    }

    /**
     * 10个线程执行任务
     */
    public static void AllThread() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        long begin = System.currentTimeMillis();

        List<Future> fl = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    if(finalI/2==0){
                        Thread.sleep(3000);
                    }else{
                        Thread.sleep(1000);
                    }
                    String cuName = Thread.currentThread().getName()+"-----"+ finalI;
                    return cuName;
                }
            });
            fl.add(future);
        }

        for (Future future : fl) {
            String r = (String) future.get();
            System.out.println(r);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - begin));

        executorService.shutdown();
    }


    public static void ListenAbleFuture() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<String> fl = Lists.newArrayList();

        ListeningExecutorService service = MoreExecutors.listeningDecorator(executorService);
            ListenableFuture<List> futereStr = service.submit(new Callable<List>() {
                @Override
                public List call() throws Exception {
                    for (int i = 0; i < 10; i++) {
                        if(i/2==0){
                            Thread.sleep(2000);
                        }else{
                            Thread.sleep(1000);
                        }
                        String cuName = Thread.currentThread().getName()+"-----"+i;
                        fl.add(cuName);
                    }
                    return fl;
                }
            });
            List<String> r = futereStr.get();
            System.out.println(r);
            //添加监听
//            futereStr.addListener(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("完成");
//                }
//            },service);
        //同步执行callback
//        Futures.addCallback(futereStr, new FutureCallback<List>() {
//            @Override
//            public void onSuccess(List result) {
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                    System.out.println("do");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                System.out.println("失败了");
//            }
//        });
        // 异步执行callback
        Futures.addCallback(futereStr, new FutureCallback<List>() {
            @Override
            public void onSuccess(List result) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("do");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        },service);
        System.out.println("完成");
    }

}
