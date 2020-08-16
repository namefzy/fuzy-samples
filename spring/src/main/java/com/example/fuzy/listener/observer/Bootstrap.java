package com.example.fuzy.listener.observer;

import java.util.Observer;

/**
 * @ClassName Bootstrap
 * @Description TODO
 * @Author 11564
 * @Date 2020/8/13 22:52
 * @Version 1.0.0
 */
public class Bootstrap {
    public static void main(String[] args) {
        // create watcher
        Watched watched = new Watched();
        // create observer
        Observer watcher = new Watcher(watched);
        // set data
        watched.setData("now");
    }
}
