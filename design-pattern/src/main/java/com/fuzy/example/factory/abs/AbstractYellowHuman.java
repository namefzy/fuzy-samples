package com.fuzy.example.factory.abs;

/**
 * @ClassName AbstractWhiteHuman
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/7 10:22
 * @Version 1.0.0
 */
public abstract class AbstractYellowHuman implements Human{
    @Override
    public void getColor() {
        System.out.println("黄色人种的皮肤是黄色的");
    }

    @Override
    public void talk() {
        System.out.println("黄色人种说话");
    }
}
