package com.fuzy.example.leetcode.editor.cn;//输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
//
// 要求时间复杂度为O(n)。 
//
// 
//
// 示例1: 
//
// 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
//输出: 6
//解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。 
//
// 
//
// 提示： 
//
// 
// 1 <= arr.length <= 10^5 
// -100 <= arr[i] <= 100 
// 
//
// 注意：本题与主站 53 题相同：https://leetcode-cn.com/problems/maximum-subarray/ 
//
// 
// Related Topics 数组 分治 动态规划 
// 👍 326 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJ42 {

    public int maxSubArrays1(int[] nums){
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            nums[i]=nums[i]+ Math.max(nums[i-1],0);
            res = Math.max(res,nums[i]);
        }
        return res;
    }

    public int maxSubArray(int[] nums) {
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            sum +=nums[i];
            max = Math.max(max,sum);
            //前面计算的结果为负数，则丢弃该段结果，因为这段结果对于后面的结果是副作用
            if(sum<0){
                sum = 0;
            }
        }
        return max;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
