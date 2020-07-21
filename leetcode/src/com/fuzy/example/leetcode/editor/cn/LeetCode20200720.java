package com.fuzy.example.leetcode.editor.cn;

/**
 * @author fuzy
 * @version 1.0.0
 * @Description
 * @date 2020/7/20 10:38
 */
public class LeetCode20200720 {
    public static void main(String[] args) {
        int[] nums = {3,2,2,3};
        int val = 2;
        int i = removeElement1(nums, val);
        System.out.println(i);
    }

    public static int removeElement1(int[] nums, int val) {
        int q = 0;
        for (int i = 0; i < nums.length;i++) {
            if(nums[i]==val){
                for (int j=i+1;j<nums.length;j++){
                    if(nums[j]!=val){
                        int temp = nums[i];
                        nums[i]=nums[j];
                        nums[j]=temp;
                        q++;
                        break;
                    }
                }
            }else{
                q++;
            }
        }

        return q;
    }
}
