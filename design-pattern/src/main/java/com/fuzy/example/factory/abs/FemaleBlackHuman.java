package com.fuzy.example.factory.abs;

/**
 * @ClassName FemaleYellowHuman
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/7 10:25
 * @Version 1.0.0
 */
public class FemaleBlackHuman extends AbstractBlackHuman{
    @Override
    public void getSex() {
        System.out.println("黄种人女性");
    }
}
