package com.fuzy.example.single;

/**
 * @ClassName LazySingleton
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/6 8:10
 * @Version 1.0.0
 */
public class LazySingleton3 {
    private static LazySingleton3 singleton = null;

    private LazySingleton3(){

    }
    public static LazySingleton3 getInstance(){
        if(singleton==null){
            synchronized (LazySingleton3.class){
                if(singleton==null){
                    return new LazySingleton3();
                }
                return singleton;
            }
        }
        return singleton;
    }
}
