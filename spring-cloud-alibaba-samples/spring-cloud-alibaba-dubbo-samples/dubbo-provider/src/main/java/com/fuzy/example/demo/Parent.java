package com.fuzy.example.demo;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2020/8/11 11:27
 */
public class Parent {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void run(){
        System.out.println("我的名字是："+name+"，我今年"+age+"岁");
    }
}
