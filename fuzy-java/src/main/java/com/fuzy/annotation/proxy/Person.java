package com.fuzy.annotation.proxy;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
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
