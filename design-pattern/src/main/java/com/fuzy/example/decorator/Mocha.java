package com.fuzy.example.decorator;

/**
 * @ClassName Mocha
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/20 6:57
 * @Version 1.0.0
 */
public class Mocha extends CondimentDecorator {

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return 1 + beverage.cost();
    }
}
