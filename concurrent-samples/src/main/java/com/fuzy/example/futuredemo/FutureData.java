package com.fuzy.example.futuredemo;

/**
 * @ClassName FutureData
 * @Description Future包装类
 * @Author 11564
 * @Date 2020/8/18 23:00
 * @Version 1.0.0
 */
public class FutureData<T> {
    private boolean isReady = false;
    private T data;

    public synchronized void setData(T data){
        this.isReady = true;
        this.data = data;
        notify();
    }

    public synchronized T getData(){
        while (!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
