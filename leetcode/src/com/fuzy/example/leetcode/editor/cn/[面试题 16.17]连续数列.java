package com.fuzy.example.leetcode.editor.cn;//给定一个整数数组，找出总和最大的连续数列，并返回总和。
//
// 示例： 
//
// 输入： [-2,1,-3,4,-1,2,1,-5,4]
//输出： 6
//解释： 连续子数组 [4,-1,2,1] 的和最大，为 6。
// 
//
// 进阶： 
//
// 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。 
// Related Topics 数组 分治算法 动态规划 
// 👍 60 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution120 {
    public int maxSubArray(int[] nums) {
        int max = 0;
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            max += nums[i];
            if(max>result){
                result = max;
            }
            //重置 因为复数会拉低值
            if(max<=0){
                max = 0;
            }
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
