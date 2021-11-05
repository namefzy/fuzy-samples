package com.fuzy.example.io;

import java.io.Serializable;

/**
 * @author fuzy
 * @date 2021/11/1 22:25
 */
public class Employee implements Serializable{

    private static final long serialVersionUID = -2301305139017630676L;

    //transient：反序列化,字段加上该属性后，该字段的值不会被序列化到文本中
    private transient String name;

    private int id;

    public Employee(String name,int id){
        this.name = name;
        this.id = id;
    }

    public void showInfo(){
        System.out.println("name:" + name + "\tid:" + id );
    }
}
