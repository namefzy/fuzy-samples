package com.fuzy.example.leetcode.editor.cn;//给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
//
// 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。 
//
// 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。 
//
// 示例 1: 
//
// 输入: [3,3,5,0,0,3,1,4]
//输出: 6
//解释: 在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
//     随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。 
//
// 示例 2: 
//
// 输入: [1,2,3,4,5]
//输出: 4
//解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。   
//     注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。   
//     因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
// 
//
// 示例 3: 
//
// 输入: [7,6,4,3,1] 
//输出: 0 
//解释: 在这个情况下, 没有交易完成, 所以最大利润为 0。 
// Related Topics 数组 动态规划 
// 👍 561 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution98 {
    public int maxProfit(int[] prices) {
        if(prices==null||prices.length==0){
            return 0;
        }
        return dfs(prices,0,0,0);
    }

    public int maxProfit1(int[] prices){
        if(prices==null||prices.length==0){
            return 0;
        }
        int n = prices.length;
        //n天数，3代表状态（0买入，1卖出，2不动），2代表交易次数
        //定义三维数组，第i天、交易了多少次、当前的买卖状态
        int[][][] dp = new int[n][3][2];
        //不动
        dp[0][0][0] = 0;
        //交易一次
        dp[0][0][1] = -prices[0];
        //卖出
        dp[0][1][0] = 0;
        //买入交易一次
        dp[0][1][1] = -prices[0];
        dp[0][2][0] = 0;
        //不动交易一次
        dp[0][2][1] = -prices[0];
        return 0;
    }

    /**
    *@Description
    *@Param index 天数
    *@Param status 股票天数
    *@Param k 交易次数
    *@Return
    *@Author fuzy
    *@Date 2020/11/28
    */
    private int dfs(int[] prices, int index, int status, int k) {
        if(index==prices.length||k==2){
            return 0;
        }
        int a=0,b=0,c=0;
        //要么不动和卖出，要么不动和买入
        //不动
        a = dfs(prices,index+1,status,k);
        if(status==1){
            //卖出
            b = dfs(prices,index+1,status,k)+prices[index];
        }else{
            //买入
            c = dfs(prices,index+1,status,k)-prices[index];
        }
        return Math.max(Math.max(a,b),c);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
