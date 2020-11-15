package com.fuzy.example.intermediary;

/**
 * @ClassName Calender
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/15 14:01
 * @Version 1.0.0
 */
public class Calender extends Colleague {
    @Override
    public void onEvent(Mediator mediator) {
        mediator.doEvent("calender");
    }

    public void doCalender() {
        System.out.println("doCalender()");
    }
}
