package com.fuzy.example.state;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/25 7:29
 * @Version 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        CandyMachine mCandyMachine = new CandyMachine(6);
        //执行准备动作
        mCandyMachine.printstate();

        //执行有硬币行为动作
        mCandyMachine.insertCoin();
        mCandyMachine.printstate();

        mCandyMachine.turnCrank();

        mCandyMachine.printstate();

        mCandyMachine.insertCoin();
        mCandyMachine.printstate();

        mCandyMachine.turnCrank();

        mCandyMachine.printstate();
    }
}
