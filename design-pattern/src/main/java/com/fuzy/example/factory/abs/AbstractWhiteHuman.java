package com.fuzy.example.factory.abs;

/**
 * @ClassName AbstractWhiteHuman
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/7 10:22
 * @Version 1.0.0
 */
public abstract class AbstractWhiteHuman implements Human{
    @Override
    public void getColor() {
        System.out.println("白色人种的皮肤是白色的");
    }

    @Override
    public void talk() {
        System.out.println("白色人种说话");
    }
}
