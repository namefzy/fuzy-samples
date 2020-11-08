package com.fuzy.example.builder.model2;

/**
 * @ClassName Builder
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/8 11:27
 * @Version 1.0.0
 */
public abstract class Builder {
    //汉堡
    abstract Builder bulidA(String mes);
    //饮料
    abstract Builder bulidB(String mes);
    //薯条
    abstract Builder bulidC(String mes);
    //甜品
    abstract Builder bulidD(String mes);
    //获取套餐
    abstract Product build();
}
