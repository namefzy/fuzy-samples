package com.fuzy.example.command;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/15 17:02
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        Receiver receiver1 = new ConcreteReciver1();
        Command command = new ConcreteCommand1(receiver1);
        invoker.setCommand(command);
        invoker.action();

//        Receiver receiver2 = new ConcreteReciver2();
//        Command command1 = new ConcreteCommand2(receiver2);
//        invoker.setCommand(command1);
//        invoker.action();

    }
}
