package com.fuzy.example.factory.abs;

/**
 * @ClassName FemaleFactory
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/7 10:30
 * @Version 1.0.0
 */
public class FemaleFactory implements HumanFactory{
    @Override
    public Human createYellowHuman() {
        return new FemaleYellowHuman();
    }

    @Override
    public Human createBlackHuman() {
        return new FemaleBlackHuman();
    }

    @Override
    public Human createWhiteHuman() {
        return new FemaleWhiteHuman();
    }
}
