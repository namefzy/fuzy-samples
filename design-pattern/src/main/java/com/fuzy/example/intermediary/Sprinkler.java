package com.fuzy.example.intermediary;

/**
 * @ClassName Sprinkler
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/15 14:01
 * @Version 1.0.0
 */
public class Sprinkler extends Colleague {
    @Override
    public void onEvent(Mediator mediator) {
        mediator.doEvent("sprinkler");
    }

    public void doSprinkler() {
        System.out.println("doSprinkler()");
    }
}