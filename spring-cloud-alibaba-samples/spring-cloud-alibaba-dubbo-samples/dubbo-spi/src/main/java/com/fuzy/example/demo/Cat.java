package com.fuzy.example.demo;

/**
 * @ClassName Cat
 * @Description TODO
 * @Author 11564
 * @Date 2020/8/2 16:09
 * @Version 1.0.0
 */
public class Cat extends Animal{

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Animal animal = Cat.class.newInstance();
        animal.eat();
    }

    public Cat() {
        System.out.println("测试反射是否能触发构造方法");
    }

    @Override
    public void eat() {
        System.out.println("猫吃饭");
    }
}
