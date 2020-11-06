package com.fuzy.example.single;

/**
 * @ClassName EnumSingleton
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/6 8:30
 * @Version 1.0.0
 */
public enum  EnumSingleton {
    INSTANCE;
    private Object data;
    public  Object getData(){
        return data;
    }
    public void setData(Object data){
        this.data = data;
    }
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
