package com.example.fuzy.listener.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @ClassName Watcher
 * @Description 监听者
 * @Author 11564
 * @Date 2020/8/13 22:51
 * @Version 1.0.0
 */
public class Watcher implements Observer {

    public Watcher(Observable o) {
        o.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("statue changed：" + ((Watched) o).getData());
    }

}
