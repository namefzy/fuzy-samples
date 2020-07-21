package com.fuzy.example.driver;

/**
 * @ClassName MysqlDriver
 * @Description TODO
 * @Author 11564
 * @Date 2020/7/18 15:53
 * @Version 1.0.0
 */
public class MysqlDriver implements Driver {
    @Override
    public String connect() {
        return "连接mysql数据库";
    }
}
