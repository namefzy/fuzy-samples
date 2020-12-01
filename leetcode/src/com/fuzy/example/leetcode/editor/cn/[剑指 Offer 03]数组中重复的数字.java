package com.fuzy.example.leetcode.editor.cn;//找出数组中重复的数字。
//
// 
//在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请
//找出数组中任意一个重复的数字。 
//
// 示例 1： 
//
// 输入：
//[2, 3, 1, 0, 2, 5, 3]
//输出：2 或 3 
// 
//
// 
//
// 限制： 
//
// 2 <= n <= 100000 
// Related Topics 数组 哈希表 
// 👍 227 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution99 {
    //历中，第一次遇到数字 xx 时，将其交换至索引 xx 处；而当第二次遇到数字 xx 时，一定有 nums[x] = xnums[x]=x ，此时即可得到一组重复数字。
    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 1, 0, 2, 5, 3};
        findRepeatNumber(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
    public static int findRepeatNumber(int[] nums) {
        int i = 0;
        while(i < nums.length) {
            if(nums[i] == i) {
                i++;
                continue;
            }
            if(nums[nums[i]] == nums[i]) return nums[i];
            //2
            int tmp = nums[i];
            //1
            nums[i] = nums[tmp];

            //2
            nums[tmp] = tmp;
        }
        return -1;

    }
}
//leetcode submit region end(Prohibit modification and deletion)
