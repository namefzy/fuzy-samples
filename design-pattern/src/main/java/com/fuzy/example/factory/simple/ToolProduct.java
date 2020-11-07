package com.fuzy.example.factory.simple;

/**
 * @ClassName ToolProduct
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/7 9:18
 * @Version 1.0.0
 */
public class ToolProduct implements Product{

    @Override
    public void getProductName() {
        System.out.println("玩具产品");
    }
}
