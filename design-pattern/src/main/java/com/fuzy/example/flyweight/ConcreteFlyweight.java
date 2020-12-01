package com.fuzy.example.flyweight;

/**
 * @ClassName ConcreteFlyweight
 * @Description TODO
 * @Author 11564
 * @Date 2020/12/1 7:17
 * @Version 1.0.0
 */
public class ConcreteFlyweight implements Flyweight {

    private String intrinsicState;

    public ConcreteFlyweight(String intrinsicState) {
        this.intrinsicState = intrinsicState;
    }

    @Override
    public void doOperation(String extrinsicState) {
        System.out.println("Object address: " + System.identityHashCode(this));
        System.out.println("IntrinsicState: " + intrinsicState);
        System.out.println("ExtrinsicState: " + extrinsicState);
    }
}
