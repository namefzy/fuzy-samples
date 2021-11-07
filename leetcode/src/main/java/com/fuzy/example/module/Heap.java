package com.fuzy.example.module;

/**
 * @author fuzy
 * @date 2021/10/14 8:11
 */
public class Heap {


    public static void main(String[] args) {
        upAdjust(new int[]{1,3,2,6,5,7,8,9,10,0});
    }

    public static void upAdjust(int[] array){
        int childIndex = array.length-1;
        int parentIndex = (childIndex-1)/2;
        int temp = array[childIndex];
        while (childIndex>0&&temp<array[parentIndex]){
            array[childIndex] = array[parentIndex];
            childIndex = parentIndex;
            parentIndex = (parentIndex-1)/2;
        }
        array[childIndex] = temp;
    }
}
