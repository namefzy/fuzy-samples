package com.fuzy.example.single;

/**
 * @ClassName LazySingleton
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/6 8:10
 * @Version 1.0.0
 */
public class LazySingleton1 {
    private static LazySingleton1 singleton = null;

    private LazySingleton1(){

    }
    public static LazySingleton1 getInstance(){
        if(singleton==null){
            return new LazySingleton1();
        }
        return singleton;
    }
}
