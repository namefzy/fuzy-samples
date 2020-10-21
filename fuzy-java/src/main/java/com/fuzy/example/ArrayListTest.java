package com.fuzy.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName ArrayListTest
 * @Description TODO
 * @Author 11564
 * @Date 2020/10/14 20:29
 * @Version 1.0.0
 */
public class ArrayListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String s = iterator.next();
            if(s.equals("2")){
                list.add("3");
            }
            System.out.println(s);
        }



//        int[] arrays= new int[]{1,2,3,4,5};
//        System.arraycopy(arrays,1,arrays,2,2);
//        for (int array : arrays) {
//            System.out.println(array);
//        }
    }
}
