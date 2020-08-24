package com.fuzy.example.futuredemo1;

import java.util.concurrent.*;

/**
 * @ClassName FutureDemo1
 * @Description TODO
 * @Author 11564
 * @Date 2020/8/18 23:10
 * @Version 1.0.0
 */
class FutureBootstrap1{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        Thread thread = new Thread(new RunnableDemo());
        Future<?> data = cachedThreadPool.submit(thread);
        Object o = data.get();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程");
    }
    public static class RunnableDemo implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子进程");
        }
    }
}
