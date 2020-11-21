package com.fuzy.example.observer;

/**
 * @ClassName ConcreteSubject
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/21 14:01
 * @Version 1.0.0
 */
public class ConcreteSubject extends Subject{

    public void doSomething(){
        super.notifyObservers();
    }
}
