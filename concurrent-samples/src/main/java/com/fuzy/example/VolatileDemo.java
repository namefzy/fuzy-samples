package com.fuzy.example;

import org.junit.Test;

/**
 * @ClassName VolatileDemo
 * @Description TODO
 * @Author fuzy
 * @Date 2021/5/22 15:50
 * @Version 1.0
 */
public class VolatileDemo {
    int a = 0;
    boolean flag = false;
    public void writer() {
        a = 1;                   //1
        flag = true;             //2
    }
    public void reader() {
        if (flag) {                //3
            int i =  a * a;        //4
            System.out.println(i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();
        Thread thread = new Thread(()->{
            demo.writer();
        });
        Thread thread1 = new Thread(()->{
            demo.reader();
        });

        thread.start();
        thread1.start();
        Thread.sleep(1000);
        System.out.println("线程结束");
    }

}
