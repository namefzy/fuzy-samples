package com.fuzy.example.decorator;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/20 6:57
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        Beverage beverage = new HouseBlend();
        beverage = new Mocha(beverage);
        beverage = new Milk(beverage);
    }
}
