package com.fuzy.example.factory.simple;

/**
 * @ClassName SimpleFactory
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/7 9:20
 * @Version 1.0.0
 */
public class SimpleFactory {

    public void createProduct(int type){
        if(type==1){
            Product product = new ToolProduct();
            product.getProductName();
        }else{
            Product product = new CloudProduct();
            product.getProductName();;
        }
    }
}
