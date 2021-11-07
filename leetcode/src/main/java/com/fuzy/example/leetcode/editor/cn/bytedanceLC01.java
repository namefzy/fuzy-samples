package com.fuzy.example.leetcode.editor.cn;

import java.util.Arrays;

/**
 * @author fuzy
 * @version 1.0
 * @Description

 * @date 2021/3/2 18:03
 */
public class bytedanceLC01 {
    public static void main(String[] args) {
        int i = splitFindMax(5, new int[]{4, 2, 6, 5,7, 18});
        System.out.println(i);
    }

    public static int splitFindMax(int k,int[] arr){
        int max  = 0;

        //先排序
        Arrays.sort(arr);
        //k个合法的数字
        int[] copyArr = new int[k];
        //从n-k,n-1位置生成一个新数组
        System.arraycopy(arr,arr.length-k,copyArr,0,k);

        int left = 0;
        int right = k-1;
        //4, 2, 6, 5,7, 18
        while (left<right){
            int n = copyArr[right]/copyArr[left];
            if(right-left+1+n<=k){
                break;
            }
            if(n>0){
                left = left+1;
            }else{
                break;
            }
        }
        return copyArr[left-1];
    }
}
