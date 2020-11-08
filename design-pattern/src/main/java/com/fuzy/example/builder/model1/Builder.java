package com.fuzy.example.builder.model1;

/**
 * @ClassName Builder
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/8 11:07
 * @Version 1.0.0
 */
public abstract class Builder {

    //地基
    abstract void bulidA();
    //钢筋工程
    abstract void bulidB();
    //铺电线
    abstract void bulidC();
    //粉刷
    abstract void bulidD();
    //完工-获取产品
    abstract Product getProduct();
}
