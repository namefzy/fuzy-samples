package com.fuzy.example.factory.normal;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/7 9:57
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        AbstractProductFactory abstractProductFactory = new CloudProductFactory();
        abstractProductFactory.factoryMethod().getProductName();
        AbstractProductFactory abstractProductFactory1 = new ToolProductFactory();
        abstractProductFactory.factoryMethod().getProductName();
    }
}
