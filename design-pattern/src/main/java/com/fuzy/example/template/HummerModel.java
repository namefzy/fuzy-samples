package com.fuzy.example.template;

/**
 * @ClassName HummerModel
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/8 10:11
 * @Version 1.0.0
 */
public abstract class HummerModel {

    protected abstract void start();

    protected abstract void stop();

    protected abstract void alarm();

    protected abstract void engineBoom();

    //钩子方法
    protected boolean isAlarm(){
        return true;
    }

    final public void run(){
        this.start();
        this.engineBoom();
        if(this.isAlarm()){
            this.alarm();
        }
        this.stop();
    }
}
