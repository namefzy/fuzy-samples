package com.fuzy.example.memento;

/**
 * @ClassName Memento
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 9:07
 * @Version 1.0.0
 */
public class Memento {

    private String state;
    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
