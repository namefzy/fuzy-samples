package com.fuzy.example.driver;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @ClassName Bootstrap
 * @Description dubbo扩展实现
 * @Author 11564
 * @Date 2020/7/19 20:23
 * @Version 1.0.0
 */
public class DriverBootstrap {
    public static void main(String[] args) {
        ExtensionLoader<Driver> extensionLoader = ExtensionLoader.getExtensionLoader(Driver.class);
        Driver mysqlDriver = extensionLoader.getExtension("mysqlDriver");
        System.out.println(mysqlDriver.connect());
    }
}
