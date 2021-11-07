package com.fuzy.example.leetcode.editor.cn;

/**
 * @author fuzy
 * @version 1.0
 * @Description 冒泡排序
 * @date 2021/1/26 18:03
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1,3,2,4,5};
        BubbleSort(arr);
        for (int i : arr) {
            System.out.print(i+"\r\n");
        }
    }

    /**
     * 先找出最大的元素
     * 将数组[1,3,2,4,5] 转换为 [5,4,3,2,1]
     * @param arr
     */
    public static void BubbleSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if(arr[j]>arr[i]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

    }
}
