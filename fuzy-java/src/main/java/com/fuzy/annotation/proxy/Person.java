package com.fuzy.annotation.proxy;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/5/12 11:25
 */

public interface Person {

    /**
     * 生成实现Person的代理类
     * @return
     */
    @Proxy
    String sayHello();
}
