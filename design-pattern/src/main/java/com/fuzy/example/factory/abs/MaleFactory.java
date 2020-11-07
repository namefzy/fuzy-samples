package com.fuzy.example.factory.abs;

/**
 * @ClassName MaleFactory
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/7 10:31
 * @Version 1.0.0
 */
public class MaleFactory implements HumanFactory{
    @Override
    public Human createYellowHuman() {
        return new MaleYellowHuman();
    }

    @Override
    public Human createBlackHuman() {
        return new MaleBlackHuman();
    }

    @Override
    public Human createWhiteHuman() {
        return new MaleWhiteHuman();
    }
}
