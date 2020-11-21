package com.fuzy.example.iterator;

import java.util.Vector;

/**
 * @ClassName ConcreteAggregate
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/21 10:53
 * @Version 1.0.0
 */
public class ConcreteAggregate implements Aggregate{

    private Vector vector = new Vector();


    @Override
    public void add(Object object) {
        vector.add(object);
    }

    @Override
    public void remove(Object object) {
        vector.remove(object);
    }

    @Override
    public Iterator iterator() {
        return new ConcreteIterator(vector);
    }
}
