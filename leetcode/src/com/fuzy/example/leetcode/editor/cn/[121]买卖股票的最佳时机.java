package com.fuzy.example.leetcode.editor.cn;
//给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
//
// 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。 
//
// 注意：你不能在买入股票前卖出股票。 
//
// 
//
// 示例 1: 
//
// 输入: [7,1,5,3,6,4]
//输出: 5
//解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
//     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
// 
//
// 示例 2: 
//
// 输入: [7,6,4,3,1]
//输出: 0
//解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
// 
// Related Topics 数组 动态规划 
// 👍 1131 👎 0


import javax.swing.*;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution20 {
    public int maxProfit(int[] prices) {
        int max = 0;
        for (int i = 0; i < prices.length; i++) {
            int temp = 0;
            for (int j = i+1; j < prices.length; j++) {
                temp = Math.max(temp, prices[j] - prices[i]);
            }
            max = Math.max(temp,max);
        }
        return max;
    }

    public int maxProfit2(int[] prices){
        int minPrice = 0,maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if (prices[i] - minPrice > maxProfit) {
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }
    public int maxProfit1(int[] prices){
        int length = prices.length;
        if(length<2){
            return 0;
        }
        int[][] dp = new int[length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i <length ; i++) {
            //不持股 昨天不持股，现金数不变；昨天持股的，说明今天卖出，现金数+今天卖出的钱
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
            //持股 昨天不持股，今天持股，现金-prices[i];昨天持股，今天还持股，说明没有卖出，现金数不变。
            dp[i][1] = Math.max(dp[i-1][1],-prices[i]);
        }
        return dp[length-1][0];
    }

    public int maxProfix2(int[] prices){
        int len = prices.length;
        if(len<2){
            return 0;
        }

        int[] dp = new int[2];
        dp[0] = 0;
        dp[1] = -prices[0];
        for (int i = 0; i < len; i++) {
            dp[0] = Math.max(dp[0],dp[1]+prices[i]);
            dp[1] = Math.max(dp[1],-prices[i]);
        }
        return dp[0];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
