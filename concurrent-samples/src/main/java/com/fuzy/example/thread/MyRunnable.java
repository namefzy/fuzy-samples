package com.fuzy.example.thread;

/**
 * @ClassName MyRunable
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 22:28
 * @Version 1.0.0
 */
public class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        MyRunnable instance = new MyRunnable();
        Thread thread = new Thread(instance);
        thread.start();
    }
}
