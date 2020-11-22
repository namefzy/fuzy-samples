package com.fuzy.example.visitor;

import java.util.*;

/**
 * @ClassName Order
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 9:36
 * @Version 1.0.0
 */
public class Order implements Element{
    private String name;
    private List<Item> items = new ArrayList();

    Order(String name) {
        this.name = name;
    }

    Order(String name, String itemName) {
        this.name = name;
        this.addItem(new Item(itemName));
    }

    String getName() {
        return name;
    }

    void addItem(Item item) {
        items.add(item);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);

        for (Item item : items) {
            item.accept(visitor);
        }
    }
}
