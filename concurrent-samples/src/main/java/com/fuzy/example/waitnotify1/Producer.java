package com.fuzy.example.waitnotify1;

import java.util.Queue;

/**
 * @ClassName Producer
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 23:03
 * @Version 1.0.0
 */
public class Producer implements Runnable{

    private Queue<Integer> queue;

    private Integer i = 0;

    public Producer(Queue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            synchronized (queue){
                while (queue.size()==10){
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.add(i++);
                System.out.println("生产者："+i);
                queue.notifyAll();
            }
        }

    }
}
