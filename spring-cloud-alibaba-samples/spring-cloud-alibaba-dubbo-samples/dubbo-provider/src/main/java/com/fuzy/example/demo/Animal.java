package com.fuzy.example.demo;

import javax.annotation.PostConstruct;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2020/8/10 14:35
 */
public abstract class Animal implements Person{

    public void init(String name,String age){
        System.out.println(name+":"+age);
    }
}
