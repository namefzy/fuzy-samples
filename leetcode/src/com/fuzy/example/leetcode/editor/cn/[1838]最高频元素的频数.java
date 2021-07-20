package com.fuzy.example.leetcode.editor.cn;//元素的 频数 是该元素在一个数组中出现的次数。
//
// 给你一个整数数组 nums 和一个整数 k 。在一步操作中，你可以选择 nums 的一个下标，并将该下标对应元素的值增加 1 。 
//
// 执行最多 k 次操作后，返回数组中最高频元素的 最大可能频数 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,4], k = 5
//输出：3
//解释：对第一个元素执行 3 次递增操作，对第二个元素执 2 次递增操作，此时 nums = [4,4,4] 。
//4 是数组中最高频元素，频数是 3 。 
//
// 示例 2： 
//
// 
//输入：nums = [1,4,8,13], k = 5
//输出：2
//解释：存在多种最优解决方案：
//- 对第一个元素执行 3 次递增操作，此时 nums = [4,4,8,13] 。4 是数组中最高频元素，频数是 2 。
//- 对第二个元素执行 4 次递增操作，此时 nums = [1,8,8,13] 。8 是数组中最高频元素，频数是 2 。
//- 对第三个元素执行 5 次递增操作，此时 nums = [1,4,13,13] 。13 是数组中最高频元素，频数是 2 。
// 
//
// 示例 3： 
//
// 
//输入：nums = [3,9,6], k = 2
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 105 
// 1 <= nums[i] <= 105 
// 1 <= k <= 105 
// 
// Related Topics 数组 二分查找 前缀和 滑动窗口 
// 👍 136 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution1838 {


    /**
     * 暴力解法可能得思路：
     *    [1,2,2,4,4,4,7,8] k=6
     *    先计算1得个数；索引变成1，在计算2得个数；索引此时变成3，计算4的个数；索引变成6，计算7的个数；索引变成7计算8的个数。
     *    每次计算个数的时候从后往前推，因为右边的数比左边的数更接近当前数。
     * @param nums
     * @param k
     * @return
     */
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        long total = 0;
        int l=0,res=1;
        for (int r = 1; r < n; r++) {
            //使从[nums[l],nums[r])区间所有的数都等于nums[r]的总和total
            //比如示例[1,2,4，7],k=5 第一次循环total=1,第二次循环total=5，第三次是14
            total +=(long)(nums[r]-nums[r-1])*(r-l);
            //如果total>k，说明k不能满足从[nums[l],nums[r])区间所有的数都等于nums[r]的总和total，收缩左区间；
            while (total>k){
                //还是以[1,2,4，7],k=5为例，当total=14时，则收缩左区间，此时编程2，4，7；total=14-(7-1)=8
                //当total=8时，依然>5,此时再次计算total=8-5=3，k<5则结束得到 res=2 小于[1,2,4]的结果，则得res=3
                total -=nums[r]-nums[l];
                ++l;
            }
            //计算的是
            res = Math.max(res,r-l+1);
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
