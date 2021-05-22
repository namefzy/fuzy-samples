package com.fuzy.example.syc;

/**
 * @ClassName SynchronizedDemo
 * @Description TODO
 * @Author fuzy
 * @Date 2021/5/19 7:14
 * @Version 1.0
 */
public class SynchronizedDemo {

    public  void say(){
        synchronized (this){
            System.out.println("--------");
        }

    }
}
