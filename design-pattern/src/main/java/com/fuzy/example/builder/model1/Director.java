package com.fuzy.example.builder.model1;

/**
 * @ClassName Director
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/8 11:08
 * @Version 1.0.0
 */
public class Director {
    //指挥工人按顺序造房
    public Product create(Builder builder) {
        builder.bulidA();
        builder.bulidB();
        builder.bulidC();
        builder.bulidD();
        return builder.getProduct();
    }
}
