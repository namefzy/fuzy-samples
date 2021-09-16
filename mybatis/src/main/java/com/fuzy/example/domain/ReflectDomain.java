package com.fuzy.example.domain;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/5/28 17:40
 */
public class ReflectDomain {

    private String name;

    private int age;

    public ReflectDomain() {
    }

    public ReflectDomain(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println(name);
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println(age);
        this.age = age;
    }

    private void say(){
        System.out.println("Hello World!");
    }

    public void say(String msg){
        System.out.println(msg);
    }
}
