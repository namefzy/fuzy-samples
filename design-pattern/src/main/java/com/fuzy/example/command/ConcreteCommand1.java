package com.fuzy.example.command;

/**
 * @ClassName ConcreteCommand1
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/15 16:57
 * @Version 1.0.0
 */
public class ConcreteCommand1 extends Command{

    private Receiver receiver;

    public ConcreteCommand1(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void exec() {
        receiver.doSomething();
    }
}
