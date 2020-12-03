package com.example.demo.thread;

public class InterrupterTest3 {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+">>>>>>>>>>>>>>>>>>"+i);
        }

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    for (int i = 0; i < 10; i++) {
                        try {
                            if(i==2){
                                this.wait(20_000);//当i==2时等待20秒，超时后继续运行
                            }
//                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            //InterruptedException将状态还原所以显示（false）
                            System.out.println(Thread.currentThread().getName()+ ":"+ Thread.interrupted());
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName()+"-------------"+i);
                    }
                }
            }
        });

        t1.start();
        //main线程中断t1
        t1.interrupt();
        //t1是否中断(true)
        System.out.println(t1.isInterrupted());



    }
}
