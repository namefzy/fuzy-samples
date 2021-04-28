package com.fuzy.example.leetcode.editor.cn;//给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和
//。假定每组输入只存在唯一答案。 
//
// 
//
// 示例： 
//
// 输入：nums = [-1,2,1,-4], target = 1
//输出：2
//解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
// 
//
// 
//
// 提示： 
//
// 
// 3 <= nums.length <= 10^3 
// -10^3 <= nums[i] <= 10^3 
// -10^4 <= target <= 10^4 
// 
// Related Topics 数组 双指针 
// 👍 764 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution016 {
    /**
     * 找出所有的三个数的组合，然后进行比较
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int ans = nums[0]+nums[1]+nums[2];
        for (int i = 0; i < nums.length; i++) {
            int start = i+1,end = nums.length-1;
            while (start<end){
                int sum = nums[i]+nums[start]+nums[end];
                //比较上一次与本次的值谁最接近
                if(Math.abs(target-sum)<Math.abs(target-ans)){
                    ans = sum;
                }else if(sum>target){
                    //计算result与target的差值，与上一次比较，如果该次比较接近继续while循环，如果上一次比较接近，执行for循环
                    end--;
                }else if(sum<target){
                    //如果sum小于target，继续往后遍历
                    start++;
                }else{
                    //sum==target 直接return ans;
                    return ans;
                }
            }
        }

        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
