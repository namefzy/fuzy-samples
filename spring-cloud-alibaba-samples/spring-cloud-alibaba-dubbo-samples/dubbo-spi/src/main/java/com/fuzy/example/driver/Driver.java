package com.fuzy.example.driver;

import org.apache.dubbo.common.extension.SPI;

/**
 * @ClassName Dirver
 * @Description TODO
 * @Author 11564
 * @Date 2020/7/19 20:20
 * @Version 1.0.0
 */
@SPI
public interface Driver {
    String connect();
}
