package com.fuzy.example.domain;

import lombok.Data;

/**
 * @ClassName B
 * @Description TODO
 * @Author fuzy
 * @Date 2021/1/30 10:48
 * @Version 1.0
 */
@Data
public class B {

    private String name;

    private A a;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
