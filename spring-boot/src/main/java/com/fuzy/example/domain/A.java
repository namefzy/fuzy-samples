package com.fuzy.example.domain;

import lombok.Data;

/**
 * @ClassName A
 * @Description TODO
 * @Author fuzy
 * @Date 2021/1/30 10:48
 * @Version 1.0
 */
@Data
public class A {

    private String name;

    private B b;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        a.setB(b);
        b.setA(a);
        a = null;
        b = null;
    }
}
