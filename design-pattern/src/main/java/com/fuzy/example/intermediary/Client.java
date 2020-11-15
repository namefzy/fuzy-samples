package com.fuzy.example.intermediary;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/15 14:02
 * @Version 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        Alarm alarm = new Alarm();
        CoffeePot coffeePot = new CoffeePot();
        Calender calender = new Calender();
        Sprinkler sprinkler = new Sprinkler();
        Mediator mediator = new ConcreteMediator(alarm, coffeePot, calender, sprinkler);
        // 闹钟事件到达，调用中介者就可以操作相关对象
        alarm.onEvent(mediator);
    }
}
