package com.fuzy.example.proxy.dynamic;

/**
 * @ClassName RealSubject
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/10 22:35
 * @Version 1.0.0
 */
public class RealSubject implements Subject{
    @Override
    public String SayHello(String name) {
        return "hello"+name;
    }

    @Override
    public String SayGoodBye() {
        return "good bye";
    }
}
