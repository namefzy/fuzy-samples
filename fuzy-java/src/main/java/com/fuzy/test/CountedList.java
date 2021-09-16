package com.fuzy.test;

import java.util.ArrayList;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2020/12/25 11:33
 */
public class CountedList extends ArrayList<String> {
    private static int counter = 0;
    private int id = counter++;
    public CountedList() {
        System.out.println("CountedList #" + id);
    }
    public int getId() { return id; }
}
