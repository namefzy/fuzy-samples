package com.fuzy.example.waitnotify1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 23:11
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        Thread thread1 = new Thread(new Producer(queue));
        Thread thread2 = new Thread(new Consumer(queue));
        thread1.start();
        thread2.start();
    }
}
