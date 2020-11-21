package com.fuzy.example.observer;

import java.util.Vector;

/**
 * @ClassName Subject
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/21 13:57
 * @Version 1.0.0
 */
public abstract class Subject {

    private Vector<Observer> observers = new Vector<>();

    public void addObserver(Observer o){
        this.observers.add(o);
    }

    public void delObserver(Observer o){
        observers.remove(o);
    }

    public void notifyObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
