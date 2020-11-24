package com.fuzy.example.sleep;

/**
 * @ClassName SleepDemo
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/24 22:48
 * @Version 1.0.0
 */
public class SleepDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("子线程正在睡眠");
                Thread.sleep(10000);
                System.out.println("子线程已经被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        thread.start();

        //主线程休眠2s
        Thread.sleep(2000);

        //主线程中断子线程
        thread.interrupt();
    }
}
