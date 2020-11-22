package com.fuzy.example.visitor;

/**
 * @ClassName Element
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 9:33
 * @Version 1.0.0
 */
public interface Element {

    void accept(Visitor visitor);
}
