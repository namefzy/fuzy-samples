package com.example.fuzy.spring.ioc.dependency.lookup.domain;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/2/7 10:25
 */
public class Parent {

    private int id;

    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
