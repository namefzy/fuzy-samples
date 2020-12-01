package com.fuzy.example.flyweight;

import java.util.HashMap;

/**
 * @ClassName FlyweightFactory
 * @Description TODO
 * @Author 11564
 * @Date 2020/12/1 7:17
 * @Version 1.0.0
 */
public class FlyweightFactory {
    private HashMap<String, Flyweight> flyweights = new HashMap<>();

    Flyweight getFlyweight(String intrinsicState) {
        if (!flyweights.containsKey(intrinsicState)) {
            Flyweight flyweight = new ConcreteFlyweight(intrinsicState);
            flyweights.put(intrinsicState, flyweight);
        }
        return flyweights.get(intrinsicState);
    }
}
