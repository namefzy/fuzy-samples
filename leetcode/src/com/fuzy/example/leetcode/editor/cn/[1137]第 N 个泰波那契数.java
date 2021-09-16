package com.fuzy.example.leetcode.editor.cn;//泰波那契序列 Tn 定义如下：
//
// T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2 
//
// 给你整数 n，请返回第 n 个泰波那契数 Tn 的值。 
//
// 
//
// 示例 1： 
//
// 输入：n = 4
//输出：4
//解释：
//T_3 = 0 + 1 + 1 = 2
//T_4 = 1 + 1 + 2 = 4
// 
//
// 示例 2： 
//
// 输入：n = 25
//输出：1389537
// 
//
// 
//
// 提示： 
//
// 
// 0 <= n <= 37 
// 答案保证是一个 32 位整数，即 answer <= 2^31 - 1。 
// 
// Related Topics 记忆化搜索 数学 动态规划 
// 👍 112 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution1137 {
    /**
     * 动态规划解法
     * @param n
     * @return
     */
    public int tribonacci(int n) {
        if(n==0){
            return 0;
        }
        if(n==1||n==2){
            return 1;
        }
        long[] nums = new long[n+1];
        nums[0] = 0;
        nums[1] = 1;
        nums[2] = 1;
        for (int i = 3; i < n+1; i++) {
            nums[i] = nums[i-3]+nums[i-2]+nums[i-1];
        }
        return (int)nums[n];
    }

    private int helper(int n){
        if(n==0){
            return 0;
        }
        if(n==1||n==2){
            return 1;
        }
        return helper(n-3)+helper(n-2)+helper(n-1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
