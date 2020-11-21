package com.fuzy.example.iterator;

/**
 * @ClassName Aggregate
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/21 10:38
 * @Version 1.0.0
 */
public interface Aggregate {
    void add(Object object);
    void remove(Object object);
    Iterator iterator();
}
