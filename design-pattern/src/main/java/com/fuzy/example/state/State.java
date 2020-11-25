package com.fuzy.example.state;

/**
 * @ClassName State
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/25 7:25
 * @Version 1.0.0
 */
public interface State {
    public void insertCoin();
    public void returnCoin();
    public void turnCrank();
    public void dispense();
    public void printstate();
}
