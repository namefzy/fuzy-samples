package com.fuzy.example.visitor;

/**
 * @ClassName Visitor
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 9:37
 * @Version 1.0.0
 */
public interface Visitor {
    void visit(Customer customer);

    void visit(Order order);

    void visit(Item item);
}
