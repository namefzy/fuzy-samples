package com.fuzy.example.leetcode.editor.cn;//实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列（即，组合出下一个更大的整数）。
//
// 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。 
//
// 必须 原地 修改，只允许使用额外常数空间。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[1,3,2]
// 
//
// 示例 2： 
//
// 
//输入：nums = [3,2,1]
//输出：[1,2,3]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1,1,5]
//输出：[1,5,1]
// 
//
// 示例 4： 
//
// 
//输入：nums = [1]
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 100 
// 0 <= nums[i] <= 100 
// 
// Related Topics 数组 双指针 👍 1391 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution032 {


    /**
     * 思路：从后往前遍历，i是个位数，则比较 i与i-1的大小
     *                 i是十位数，则比较 十位数反转后的大小
     * //[12,35,53,63]
     *     //[1,2,3]
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int i = nums.length-2;
        while (i>=0&&nums[i]>=nums[i+1]){
            i--;
        }


        if(i>=0){
            int j = nums.length-1;
            while (j>=0&&nums[i]>=nums[j]){
                j--;

            }
            swap(nums,i,j);
        }
        reverse(nums,i+1);

    }
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)
