package com.fuzy.example.leetcode.editor.cn;//地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一
//格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但
//它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？ 
//
// 
//
// 示例 1： 
//
// 输入：m = 2, n = 3, k = 1
//输出：3
// 
//
// 示例 2： 
//
// 输入：m = 3, n = 1, k = 0
//输出：1
// 
//
// 提示： 
//
// 
// 1 <= n,m <= 100 
// 0 <= k <= 20 
// 
// 👍 261 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution1122 {
    public int movingCount(int m, int n, int k) {
        if(k==0){
            return 1;
        }
        boolean[][] vis = new boolean[m][n];
        int ans = 1;
        vis[0][0] = true;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(i==0&&j==0||get(i)+get(j)>k){
                    continue;
                }
                if(i-1>=0){
                    vis[i][j] = vis[i-1][j];
                }
                if(j-1>0){
                    vis[i][j] = vis[i][j-1];
                }
                ans +=vis[i][j]?1:0;
            }
        }
        return ans;
    }
    private int get(int x) {
        int res = 0;
        while (x != 0) {
            res += x % 10;
            x /= 10;
        }
        return res;
    }

}
//leetcode submit region end(Prohibit modification and deletion)
