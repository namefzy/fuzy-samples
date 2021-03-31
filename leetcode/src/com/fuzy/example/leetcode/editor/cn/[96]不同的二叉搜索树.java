package com.fuzy.example.leetcode.editor.cn;
//给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
//
// 示例: 
//
// 输入: 3
//输出: 5
//解释:
//给定 n = 3, 一共有 5 种不同结构的二叉搜索树:
//
//   1         3     3      2      1
//    \       /     /      / \      \
//     3     2     1      1   3      2
//    /     /       \                 \
//   2     1         2                 3 
// Related Topics 树 动态规划 
// 👍 1083 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution960 {
    //G(n)=G(0)∗G(n−1)+G(1)∗(n−2)+...+G(n−1)∗G(0)
    public int numTrees(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        //G(2) = G(1)*G(0)+G(0)*G(1)
        for(int i = 2; i < n + 1; i++)
            for(int j = 1; j < i + 1; j++)
                dp[i] += dp[j-1] * dp[i-j];

        return dp[n];
    }

}
//leetcode submit region end(Prohibit modification and deletion)
