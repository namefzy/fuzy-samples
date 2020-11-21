package com.fuzy.example.observer;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/21 14:02
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        Observer observer1 = new ConcreteObserver("1");
        Observer observer2 = new ConcreteObserver("2");
        subject.addObserver(observer1);
        subject.addObserver(observer2);
        subject.doSomething();
    }
}
