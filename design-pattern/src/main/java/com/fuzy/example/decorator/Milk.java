package com.fuzy.example.decorator;

/**
 * @ClassName Mile
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/20 6:56
 * @Version 1.0.0
 */
public class Milk extends CondimentDecorator {
    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return 1 + beverage.cost();
    }
}
