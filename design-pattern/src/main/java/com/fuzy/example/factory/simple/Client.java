package com.fuzy.example.factory.simple;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/7 9:22
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        SimpleFactory simpleFactory = new SimpleFactory();
        simpleFactory.createProduct(1);
    }
}
