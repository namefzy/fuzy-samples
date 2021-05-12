package com.example.fuzy.spring.cycle.dependency;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName CycleDependecyDemo
 * @Description 循环依赖
 * @Author fuzy
 * @Date 2021/3/15 22:48
 * @Version 1.0
 */
public class CycleDependencyDemo {

    private static ConcurrentHashMap<String,Object> chm = new ConcurrentHashMap<String,Object>();

    /**
     * 创建A对象时，发现没有则创建，在注入的时候发现需要依赖B对象；则创建B对象，同时又发现A对象存在，则先把未创建好的A对象给B；
     * @param args
     */
    public static void main(String[] args) {
        A a = new A();
        populateB();
    }

    private static void populateB() {

        if(!chm.containsKey("B")){
           B b = new B();
           populateA();
        }
    }

    private static void populateA() {

        if(chm.containsKey("A")){
            //注入A
        }
    }
}
