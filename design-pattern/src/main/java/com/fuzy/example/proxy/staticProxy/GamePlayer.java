package com.fuzy.example.proxy.staticProxy;

/**
 * @ClassName GamePlayer
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/9 7:25
 * @Version 1.0.0
 */
public class GamePlayer implements IGamePlayer{
    @Override
    public void login(String username, String password) {
        System.out.println("正在登录中");
    }

    @Override
    public void killBoss() {

    }

    @Override
    public void upgrade() {

    }
}
