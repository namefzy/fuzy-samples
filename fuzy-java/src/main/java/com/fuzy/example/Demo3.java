package com.fuzy.example;


import javafx.concurrent.Worker;

/**
 * @ClassName Demo3
 * @Description TODO
 * @Author fuzy
 * @Date 2021/1/25 21:40
 * @Version 1.0
 */
public class Demo3 {
    public static void main(String[] args) {
        //Demo3
        ClassLoader classLoader= Demo3.class.getClassLoader();

        System.out.println(classLoader);
        //Demo3
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());
    }
}
