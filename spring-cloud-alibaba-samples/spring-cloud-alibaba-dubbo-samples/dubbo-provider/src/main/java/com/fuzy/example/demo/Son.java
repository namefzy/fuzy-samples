package com.fuzy.example.demo;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2020/8/11 11:27
 */
public class Son extends Parent{

    private String name;

    private int age;

    public static void main(String[] args) {
        Son son = new Son();
        son.setAge(1);
        son.setName("小王");
        son.run();
    }
}
