package com.fuzy.example.demo;

/**
 * @ClassName ArraysDemo
 * @Description TODO
 * @Author 11564
 * @Date 2020/8/3 20:20
 * @Version 1.0.0
 */
public class ArraysDemo {
    public static void main(String[] args) {

        int[] arrays = new int[]{1,2,3,4};

        int[] copyArrays = new int[arrays.length];
        System.arraycopy(arrays,1,copyArrays,0,3);

        int length = 2;
        int array = copyArrays[length--];
        System.out.println(String.format("数组值%s,长度%s",array,length));
    }
}
