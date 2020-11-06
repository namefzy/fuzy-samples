package com.fuzy.example.single;

/**
 * @ClassName Singleton1
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/6 8:04
 * @Version 1.0.0
 */
public class Singleton1 {

    private static final Singleton1 singleton = new Singleton1();
    private Singleton1(){

    }
    public static Singleton1 getInstance(){
        return singleton;
    }
}
