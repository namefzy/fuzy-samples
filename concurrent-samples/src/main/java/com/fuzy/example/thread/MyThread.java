package com.fuzy.example.thread;

/**
 * @ClassName MyThread
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 22:30
 * @Version 1.0.0
 */
public class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
    }
}
