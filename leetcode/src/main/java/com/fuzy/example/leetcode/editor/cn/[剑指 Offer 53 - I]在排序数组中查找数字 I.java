package com.fuzy.example.leetcode.editor.cn;//统计一个数字在排序数组中出现的次数。
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [5,7,7,8,8,10], target = 8
//输出: 2 
//
// 示例 2: 
//
// 
//输入: nums = [5,7,7,8,8,10], target = 6
//输出: 0 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 105 
// -109 <= nums[i] <= 109 
// nums 是一个非递减数组 
// -109 <= target <= 109 
// 
//
// 
//
// 注意：本题与主站 34 题相同（仅返回值不同）：https://leetcode-cn.com/problems/find-first-and-last-
//position-of-element-in-sorted-array/ 
// Related Topics 数组 二分查找 
// 👍 194 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionOffer53 {
    public int search(int[] nums, int target) {
        int i = 0,j=nums.length-1;
        while (i<=j){
            int m = (i+j)/2;
            if(nums[m]<=target){
                i = m+1;
            }else {
                j = m-1;
            }
        }
        int right = i;
        if(j>=0&&nums[j]!=target){
            return 0;
        }

        i=0;j=nums.length-1;
        while (i<=j){
            int m = i+(j-i)/2;
            if(nums[m]<target){
                i = m+1;
            }else{
                j = m-1;
            }
        }
        int left = j;
        return right-left-1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
