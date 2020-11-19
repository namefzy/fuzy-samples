package com.fuzy.example.strategy;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/20 7:15
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        Duck duck = new Duck();
        duck.setQuackBehavior(new Squeak());
        duck.performQuack();
        duck.setQuackBehavior(new Quack());
        duck.performQuack();
    }
}
