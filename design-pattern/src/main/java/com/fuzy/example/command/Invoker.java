package com.fuzy.example.command;

/**
 * @ClassName Invoker
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/15 17:01
 * @Version 1.0.0
 */
public class Invoker {

    private Command command;

    public void setCommand(Command command){
        this.command = command;
    }

    public void action(){
        this.command.exec();
    }
}
