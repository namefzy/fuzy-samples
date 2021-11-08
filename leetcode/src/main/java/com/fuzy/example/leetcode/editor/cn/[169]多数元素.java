package com.fuzy.example.leetcode.editor.cn;
//给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
//
// 你可以假设数组是非空的，并且给定的数组总是存在多数元素。 
//
// 
//
// 示例 1: 
//
// 输入: [3,2,3]
//输出: 3 
//
// 示例 2: 
//
// 输入: [2,2,1,1,1,2,2]
//输出: 2
// 
// Related Topics 位运算 数组 分治算法 
// 👍 727 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution47 {
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        int length = (nums.length+1) / 2;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if(i==j){
                    count++;
                    if(count>=length){
                        return i;
                    }
                    i=j+1;
                }
            }
        }
        return nums[0];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
