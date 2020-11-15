package com.fuzy.example.chain;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/15 17:18
 * @Version 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();

        handler1.setNext(handler2);
        handler2.setNext(handler3);
        Response response = handler1.handlerMessage(new Request());
    }
}
