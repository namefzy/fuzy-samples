package com.fuzy.example.flyweight;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/12/1 7:19
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        FlyweightFactory factory = new FlyweightFactory();
        Flyweight flyweight1 = factory.getFlyweight("aa");
        Flyweight flyweight2 = factory.getFlyweight("aa");
        flyweight1.doOperation("x");
        flyweight2.doOperation("y");
    }
}
