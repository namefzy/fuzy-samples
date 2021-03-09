package com.fuzy.example.leetcode.editor.cn;
//给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
//
// 示例: 
//
// 输入: [-2,1,-3,4,-1,2,1,-5,4],
//输出: 6
//解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
// 
//
// 进阶: 
//
// 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。 
// Related Topics 数组 分治算法 动态规划 
// 👍 2211 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution3 {

    public int maxSubArray2(int[] nums) {
        int ans = nums[0];
        int sum = 0;
        for(int num: nums) {
            //如果sum>0 则sum更新
            if(sum > 0) {
                sum += num;
            } else {
                //否则sum=当前数字
                sum = num;
            }
            //ans记录最大值记录
            ans = Math.max(ans, sum);
        }
        return ans;

    }

    public static void main(String[] args) {
        int[] arr = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        int i = maxSubArray(arr);
        System.out.println(i);
    }

    public static int maxSubArray(int[] sums){
        int pre = 0;
        int maxValue = 0;
        for (int sum : sums) {
            if(maxValue+sum<=0){
                maxValue = 0;
            }else{
                maxValue = maxValue+sum;
            }
        }
        return maxValue;
    }

    public int maxSubArray1(int[] nums) {
        int size = nums.length;
        int res = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < i; j++) {
                int sum = sumOfArray(nums,j,i);
                res = Math.max(res,sum);
            }
        }
        return 0;
    }
    private int sumOfArray(int[] nums,int left,int right){
        int res = 0;
        for (int i = left; i < right; i++) {
            res +=nums[i];
        }
        return res;
    }

}
//leetcode submit region end(Prohibit modification and deletion)
