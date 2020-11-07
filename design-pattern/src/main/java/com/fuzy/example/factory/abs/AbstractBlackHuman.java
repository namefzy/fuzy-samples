package com.fuzy.example.factory.abs;

/**
 * @ClassName AbstractWhiteHuman
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/7 10:22
 * @Version 1.0.0
 */
public abstract class AbstractBlackHuman implements Human{
    @Override
    public void getColor() {
        System.out.println("黑色人种的皮肤是黑色的");
    }

    @Override
    public void talk() {
        System.out.println("黑色人种说话");
    }
}
