package com.fuzy.example.memento;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 9:08
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
        //修改状态
        caretaker.setMemento(originator.createMemento());
        originator.setState("1");

        System.out.println("修改后的状态:"+originator.getState());
        //恢复状态
        originator.restoreMemento(caretaker.getMemento());
        System.out.println("恢复原先状态:"+originator.getState());
    }
}
