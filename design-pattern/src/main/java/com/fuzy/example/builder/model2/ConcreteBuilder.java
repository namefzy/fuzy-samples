package com.fuzy.example.builder.model2;

/**
 * @ClassName ConcreteBuilder
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/8 11:28
 * @Version 1.0.0
 */
public class ConcreteBuilder extends Builder{
    private Product product;
    public ConcreteBuilder() {
        product = new Product();
    }

    @Override
    Product build() {
        return product;
    }

    @Override
    Builder bulidA(String mes) {
        product.setBuildA(mes);
        return this;
    }

    @Override
    Builder bulidB(String mes) {
        product.setBuildB(mes);
        return this;
    }

    @Override
    Builder bulidC(String mes) {
        product.setBuildC(mes);
        return this;
    }

    @Override
    Builder bulidD(String mes) {
        product.setBuildD(mes);
        return this;
    }
}
