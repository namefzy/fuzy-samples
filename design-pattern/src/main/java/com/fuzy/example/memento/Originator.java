package com.fuzy.example.memento;

/**
 * @ClassName Originator
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 9:05
 * @Version 1.0.0
 */
public class Originator {

    private String state = "5";

    public String getState(){

        return state;
    }

    public void setState(String state){
        this.state = state;
    }

    public Memento createMemento(){
        return new Memento(this.state);
    }

    public void restoreMemento(Memento memento){
        this.setState(memento.getState());
    }
}
