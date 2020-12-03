package com.example.demo.thread;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;

public class CompletionExecutorServiceTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);


        CompletionService completionService = new ExecutorCompletionService(executorService);
        long begin = System.currentTimeMillis();


        List<String> strs = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {

            int finalI = i;
            Future fl = completionService.submit(new Callable() {

                @Override
                public Object call() throws Exception {
                    if (finalI /2== 0) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return Thread.currentThread().getName() + "----" + finalI;
                }
            });
        }


        for (int i = 0; i < 10; i++) {
            Future f = completionService.take();
            System.out.println(f.get());
        }

        System.out.println(System.currentTimeMillis()-begin);

    }



}
