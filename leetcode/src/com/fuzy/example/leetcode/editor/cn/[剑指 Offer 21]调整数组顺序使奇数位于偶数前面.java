package com.fuzy.example.leetcode.editor.cn;//输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
//
// 
//
// 示例： 
//
// 
//输入：nums = [1,2,3,4]
//输出：[1,3,2,4] 
//注：[3,1,2,4] 也是正确的答案之一。 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 50000 
// 1 <= nums[i] <= 10000 
// 
// 👍 124 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution1021 {
    public int[] exchange(int[] nums) {
        int slow = 0;
        int fast = 0;
        while(fast<nums.length){
            //快慢指针都为1，则移动
            if(nums[fast]%2==1&&nums[slow]%2==1){
                ++slow;
                ++fast;
            }else if(nums[fast]%2==1&&nums[slow]%2==0){
                //快指针是奇数，慢指针是偶数，交换；同时慢指针和快指针同一起跑线；
                int temp = nums[fast];
                nums[fast] = nums[slow];
                nums[slow]=temp;

                ++fast;
                ++slow;
            }else if(nums[fast]%2==0&&nums[slow]%2==0){
                //当快慢指针都是偶数时，移动快指针
                fast++;
            }
        }
        return nums;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
