package com.fuzy.example.iterator;

import java.util.*;

/**
 * @ClassName ConcreteAggregate
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/21 10:40
 * @Version 1.0.0
 */
public class ConcreteIterator implements Iterator{

    private Vector vector = new Vector();

    //定义游标
    public int cursor = 0;

    public ConcreteIterator(Vector vector) {
        this.vector = vector;
    }

    @Override
    public Object next() {
        if(vector.size()==cursor){
            return null;
        }else {
            return vector.get(cursor++);
        }
    }

    @Override
    public boolean haxNext() {
        return vector.size() != cursor;
    }

    @Override
    public boolean remove() {
        vector.remove(this.cursor);
        return true;
    }
}
