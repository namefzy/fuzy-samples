package com.fuzy.example.memento;

/**
 * @ClassName Caretaker
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 9:08
 * @Version 1.0.0
 */
public class Caretaker {

    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
