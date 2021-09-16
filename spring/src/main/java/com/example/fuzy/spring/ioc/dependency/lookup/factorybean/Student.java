package com.example.fuzy.spring.ioc.dependency.lookup.factorybean;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/2/8 16:29
 */
public class Student {

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

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }
}
