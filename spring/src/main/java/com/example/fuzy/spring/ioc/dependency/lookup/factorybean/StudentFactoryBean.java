package com.example.fuzy.spring.ioc.dependency.lookup.factorybean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/2/7 18:01
 */
public class StudentFactoryBean implements FactoryBean<Student> {

    @Override
    public Student getObject() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("fuzy");
        return student;
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }
}
