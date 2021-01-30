package com.fuzy.example.springboot.controller;

import com.fuzy.example.domain.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HeapController
 * @Description TODO
 * @Author fuzy
 * @Date 2021/1/29 21:52
 * @Version 1.0
 */
@RestController
public class HeapController {
    List<Person> list=new ArrayList<Person>();
    @GetMapping("/heap")
    public String heap(){
        Object object = new Object();
        //业务逻辑代码.....
        object =null;
        while(true){
            list.add(new Person());
        }


    }
}

