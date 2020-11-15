package com.fuzy.example.intermediary;

/**
 * @ClassName Alarm
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/15 13:59
 * @Version 1.0.0
 */
public class Alarm extends Colleague {

    @Override
    public void onEvent(Mediator mediator) {
        mediator.doEvent("alarm");
    }

    public void doAlarm() {
        System.out.println("doAlarm()");
    }
}
