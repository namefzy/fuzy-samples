package com.fuzy.example.single;

/**
 * @ClassName LazySingleton
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/6 8:10
 * @Version 1.0.0
 */
public class LazySingleton2 {
    private static LazySingleton2 singleton = null;

    private LazySingleton2(){

    }
    public static LazySingleton2 getInstance(){
        synchronized (LazySingleton2.class){
            if(singleton==null){
                return new LazySingleton2();
            }
            return singleton;
        }
    }
}
