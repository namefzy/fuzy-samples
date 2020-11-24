package com.fuzy.example.join;

/**
 * @ClassName JoinDemo
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/24 22:24
 * @Version 1.0.0
 */
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        });
        Thread mainThread = Thread.currentThread();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(2);
//                mainThread.interrupt();
            }
        });

        thread1.start();

        thread2.start();


        thread1.join();
        thread2.join();

        System.out.println(3);
    }
}
