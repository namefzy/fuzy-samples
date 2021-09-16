package com.example.fuzy.spring.bean;

import com.example.fuzy.spring.ioc.dependency.lookup.factorybean.Student;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @author fuzy
 * @version 1.0
 * @Description 创建bean的方式
  
 * @date 2021/2/8 17:48
 */
public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Student.class);
        //通过属性设置
        beanDefinitionBuilder
                .addPropertyValue("id",1)
                .addPropertyValue("name","fuzy");
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        System.out.println("通过属性设置获取BeanDefinition:"+beanDefinition);

        //通过AbstractBeanDefinition以及派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(Student.class);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues
                .add("id", 1)
                .add("name", "fuzy");
        // 通过 set MutablePropertyValues 批量操作属性
        genericBeanDefinition.setPropertyValues(propertyValues);
        System.out.println("通过其他方式获取"+genericBeanDefinition.getBeanClassName());
    }
}
