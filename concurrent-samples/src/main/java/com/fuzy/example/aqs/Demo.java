package com.fuzy.example.aqs;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;

/**
 * 使用自定义锁实现消费者-生产者模型
 */
public class Demo {

    final static NonReetrantLock lock = new NonReetrantLock();
    final static Condition notFull = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();

    final static Queue<String> queue = new LinkedBlockingDeque<>();
    final static int queueSize = 10;

    public static void main(String[] args) {
        Thread producer = new Thread(() -> {
            lock.lock();
            try{
                while (queue.size()==queueSize){
                    notEmpty.await();
                }
                queue.add("ele");
                notFull.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });

        Thread consumer = new Thread(()->{
            lock.lock();
            try {
                while (queue.isEmpty()){
                    notFull.await();
                }
                String poll = queue.poll();
                System.out.println(poll);
                notEmpty.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        producer.start();
        consumer.start();
    }
}
