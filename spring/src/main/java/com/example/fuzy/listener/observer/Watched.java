package com.example.fuzy.listener.observer;

import java.util.Observable;

/**
 * @ClassName Watched
 * @Description 被监听者
 * @Author 11564
 * @Date 2020/8/13 22:50
 * @Version 1.0.0
 */
public class Watched extends Observable {
    private String data = "";

    public String getData() {
        return data;
    }

    public void setData(String data) {

        if(!this.data.equals(data)){
            this.data = data;
            // 修改状态
            setChanged();
        }
        // 通知所有观察者
        notifyObservers();
    }
}
