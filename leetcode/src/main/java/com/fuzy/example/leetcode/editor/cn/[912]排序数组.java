package com.fuzy.example.leetcode.editor.cn;//给你一个整数数组 nums，请你将该数组升序排列。
//
// 
//
// 
// 
//
// 示例 1： 
//
// 输入：nums = [5,2,3,1]
//输出：[1,2,3,5]
// 
//
// 示例 2： 
//
// 输入：nums = [5,1,1,2,0,0]
//输出：[0,0,1,1,2,5]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 50000 
// -50000 <= nums[i] <= 50000 
// 
// Related Topics 数组 分治 桶排序 计数排序 基数排序 排序 堆（优先队列） 归并排序 👍 419 👎 0


import org.junit.Test;

import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution912 {

    @Test
    public void test(){
        Arrays.stream(sortArray(new int[]{5,1,1,2,0,0})).forEach(System.out::println);
    }

    public int[] sortArray(int[] nums) {
        int left = 0;
        int right = nums.length-1;

        if(nums.length<1||left>right){
            return nums;
        }
        int pivotIndex = partition(nums,left,right);
        if(pivotIndex>left){
            partition(nums,left,pivotIndex-1);
        }
        if(pivotIndex<right){
            partition(nums,pivotIndex+1,right);
        }
        return nums;
    }

    public int partition(int[] nums,int left,int right){
        int l = left;
        int pivot = nums[left];
        //该循环以left索引位置为基准值，将所有小于pivot的值放到left索引后面，并且记录最后一次小于基准值的索引位置
        for (int i = l+1; i <= right; i++) {
            //如果基准值大于后边的数，则交换l++与i的值
            if(pivot>nums[i]){
                swap(nums,++l,i);
            }
        }
        //将基准值与最后一次小于基准值的交换，确保基准值左边的都小于等于基准值，右边的大于基准值
        swap(nums,left,l);
        return l;
    }
    private void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
