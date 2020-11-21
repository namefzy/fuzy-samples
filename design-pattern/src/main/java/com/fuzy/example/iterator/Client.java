package com.fuzy.example.iterator;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/21 10:54
 * @Version 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        Aggregate agg = new ConcreteAggregate();
        agg.add("a");
        agg.add("b");
        agg.add("c");
        agg.add("d");
        Iterator iterator = agg.iterator();
        while (iterator.haxNext()){
            System.out.println(iterator.next());
        }
    }
}
