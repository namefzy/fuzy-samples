package com.fuzy.example.intermediary;

/**
 * @ClassName CoffeePot
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/15 14:00
 * @Version 1.0.0
 */
public class CoffeePot extends Colleague {
    @Override
    public void onEvent(Mediator mediator) {
        mediator.doEvent("coffeePot");
    }

    public void doCoffeePot() {
        System.out.println("doCoffeePot()");
    }
}
