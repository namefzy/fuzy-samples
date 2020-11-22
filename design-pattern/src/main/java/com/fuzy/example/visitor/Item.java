package com.fuzy.example.visitor;

/**
 * @ClassName Item
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 9:37
 * @Version 1.0.0
 */
public class Item implements Element{
    private String name;

    Item(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
