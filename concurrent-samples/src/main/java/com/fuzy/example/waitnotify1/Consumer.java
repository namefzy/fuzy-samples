package com.fuzy.example.waitnotify1;

import java.util.Queue;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 23:03
 * @Version 1.0.0
 */
public class Consumer implements Runnable{

    private Queue<Integer> queue;

    public Consumer(Queue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            synchronized (queue){
                while (queue.isEmpty()){
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
                Integer poll = queue.remove();
                System.out.println("消费者："+poll);
                queue.notifyAll();
            }
        }

    }
}
