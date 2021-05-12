package com.example.fuzy.spring.ioc.dependency.injection.proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2021/5/10 16:42
 */
public class MyFeignClientFactoryBean<T> implements FactoryBean<T> {

    /**
     * 获取代理对象
     * @return
     * @throws Exception
     */
    @Override
    public T getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
