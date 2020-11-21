package com.fuzy.example.observer;

/**
 * @ClassName ConcreteObserver
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/21 13:58
 * @Version 1.0.0
 */
public class ConcreteObserver implements Observer{

    private String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println("接收到被观察者被更新的消息，进行数据"+name+"更新！");
    }
}
