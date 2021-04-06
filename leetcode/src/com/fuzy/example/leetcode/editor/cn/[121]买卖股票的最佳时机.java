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
    public int maxProfix8(int[] prices){
        if(prices.length<=1){
            return 0;
        }
        //2代表两种状态：0-未持有，1-持有
        int[][] dbs = new int[prices.length][2];
        //第一天不持有
        dbs[0][0] = 0;
        dbs[0][1] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            //第i天不持有,应该等于前一天持有和今天卖出这两者的最大值
            dbs[i][0] = Math.max(dbs[i][i-1]+prices[i],dbs[i-1][0]);
            dbs[i][1] = Math.max(dbs[i-1][1],dbs[i-1][0]-prices[0]);
        }
        return dbs[prices.length-1][0];
    }


    public int maxProfit7(int[] prices) {
       //先确定最大值
        int length = prices.length;
        int res = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i+1; j < length; j++) {
                res = Math.max(res,prices[j]-prices[i]);
            }
        }
        return res;
    }
    public int maxProfit2(int[] prices){
        int length = prices.length;
        if(length<=1){
            return 0;
        }
        return dfs(prices,0,0,0);
    }

    public int maxProfit3(int[] prices){
        int length = prices.length;
        if(length<=1){
            return 0;
        }
        int[] sells = new int[length];
        int[] buys = new int[length];
        sells[0]=0;
        buys[0]=-prices[0];
        for (int i = 1; i < length; i++) {
            //第i天买入收益 = max(第i-1天买入收益，-当天股价)
            buys[i] = Math.max(-prices[i],buys[i-1]);
            //第i天卖出收益 = max(第i-1天卖出收益，第i-1天买入收益+当天股价)
            sells[i] = Math.max(buys[i-1]+prices[i],sells[i-1]);
        }
        return sells[length-1];
    }

    public int maxProfit4(int[] prices){
        int length = prices.length;
        if(length<=1){
            return 0;
        }
        //二维数组的后一个代表持有股票状态
        int[][] dp = new int[length][2];
        //不持有，初始化为0
        dp[0][0] = 0;
        //持有股票，则需要购买
        dp[0][1] = -prices[0];
        for (int i = 1; i < length; i++) {
            //不持有股票
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
            //持有股票
            dp[i][1] = Math.max(dp[i-1][1],-prices[i]);
        }
        return dp[length-1][0];
    }

    public int maxProfit5(int[] prices){
        int length = prices.length;
        if(length<=1){
            return 0;
        }
        int[] dp = new int[2];
        dp[1] = -prices[0];
        for (int i = 1; i < length; i++) {
            //不持有股票
            dp[0] = Math.max(dp[0],dp[1]+prices[i]);
            //持有股票
            dp[1] = Math.max(dp[1],-prices[i]);
        }
        return dp[0];
    }

    public int maxProfit6(int[] prices){
        int length = prices.length;
        if(length<=1){
            return 0;
        }
        int min = Integer.MAX_VALUE;
        int currentProfit = 0;
        for (int i = 0; i < length; i++) {
            min = Math.min(min,prices[i]);
            currentProfit = Math.max(prices[i]-min,currentProfit);
        }
        return currentProfit;
    }

    /**
     * @param prices 股票区间
     * @param index 天数
     * @param status 状态 0-买入；1-卖出
     * @param k
     * @return
     */
    private int dfs(int[] prices, int index, int status, int k) {
        //当数组执行到头，或者交易了一次结束递归
        if(index==prices.length||k==1){
            return 0;
        }
        //keep不动的值；sell卖出的值；buy买入的值
        int keep=0,sell=0,buy=0;
        keep = dfs(prices,index+1,status,k);
        //对于股票来说要么不动和卖出，要么不动和买入；不可能同时存在不动、买入和卖出三种递归
        //买入
        if(status==0){
            buy = dfs(prices,index+1,1,k)-prices[index];
        }else{
            sell = dfs(prices,index+1,0,k+1)+prices[index];
        }
        return Math.max(Math.max(keep,sell),buy);
    }

    public int maxProfit1(int[] prices){
        //先确定最小值
        if(prices.length==0){
            return 0;
        }
        int length = prices.length;
        int res = 0,min=prices[0];
        for (int i = 1; i < length; i++) {
            min = Math.min(min,prices[i]);
            res = Math.max(res,prices[i]-min);
        }
        return res;
    }

}
//leetcode submit region end(Prohibit modification and deletion)
