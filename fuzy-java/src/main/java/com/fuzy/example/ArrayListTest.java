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
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).equals("1")){
                list.remove(2);
            }
            System.out.println(list.get(i));
        }
        System.out.println(list.size());
//        for (String s : list) {
//            if("1".equals(s)){
//                list.remove(s);
//            }
//        }
//        while (iterator.hasNext()){
//            if("1".equals(iterator.next())){
//                iterator.remove();
//            }
//        }



//        int[] arrays= new int[]{1,2,3,4,5};
//        System.arraycopy(arrays,1,arrays,2,2);
//        for (int array : arrays) {
//            System.out.println(array);
//        }
    }
}
