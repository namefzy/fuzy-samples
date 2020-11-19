package com.fuzy.example.adapter;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/20 7:34
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        Target target = new ConcreteTarget();
        target.doSomething();

        Target target1 = new Adapter();
        target1.doSomething();
    }
}
