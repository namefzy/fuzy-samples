package com.fuzy.example.factory.normal;

import com.fuzy.example.factory.simple.CloudProduct;
import com.fuzy.example.factory.simple.Product;
import com.fuzy.example.factory.simple.ToolProduct;

/**
 * @ClassName ToolProductFactory
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/7 9:56
 * @Version 1.0.0
 */
public class CloudProductFactory extends AbstractProductFactory{
    @Override
    public Product factoryMethod() {
        return new CloudProduct();
    }
}
