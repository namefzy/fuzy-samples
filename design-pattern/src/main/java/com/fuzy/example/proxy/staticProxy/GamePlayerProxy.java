package com.fuzy.example.proxy.staticProxy;

/**
 * @ClassName GamePlayerProxy
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/9 7:25
 * @Version 1.0.0
 */
public class GamePlayerProxy implements IGamePlayer {

    private IGamePlayer gamePlayer = null;

    public GamePlayerProxy(IGamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    @Override
    public void login(String username, String password) {
        this.gamePlayer.login(username,password);
    }

    @Override
    public void killBoss() {

    }

    @Override
    public void upgrade() {

    }
}
