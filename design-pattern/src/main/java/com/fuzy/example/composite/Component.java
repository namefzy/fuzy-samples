package com.fuzy.example.composite;

/**
 * @ClassName Component
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/21 13:39
 * @Version 1.0.0
 */
public abstract class Component {

    protected String name;

    public Component(String name) {
        this.name = name;
    }

    public void print() {
        print(0);
    }

    abstract void print(int level);

    abstract public void add(Component component);

    abstract public void remove(Component component);
}
