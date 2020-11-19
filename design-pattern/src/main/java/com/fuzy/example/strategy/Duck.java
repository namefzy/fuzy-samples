package com.fuzy.example.strategy;

/**
 * @ClassName Duck
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/20 7:15
 * @Version 1.0.0
 */
public class Duck {
    private QuackBehavior quackBehavior;

    public void performQuack() {
        if (quackBehavior != null) {
            quackBehavior.quack();
        }
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }
}
