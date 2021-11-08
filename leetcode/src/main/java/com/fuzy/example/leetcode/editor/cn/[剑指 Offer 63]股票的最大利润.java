package com.fuzy.example.leetcode.editor.cn;//假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少？
//
// 
//
// 示例 1: 
//
// 输入: [7,1,5,3,6,4]
//输出: 5
//解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
//     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
// 
//
// 示例 2: 
//
// 输入: [7,6,4,3,1]
//输出: 0
//解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。 
//
// 
//
// 限制： 
//
// 0 <= 数组长度 <= 10^5 
//
// 
//
// 注意：本题与主站 121 题相同：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-s
//tock/ 
// Related Topics 数组 动态规划 
// 👍 152 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionOffer63 {

    /**
     * 贪心算法
     * @param prices
     * @return
     */
    public int maxProfit1(int[] prices){
        int length = prices.length;
        if(length<=1){
            return 0;
        }
        int min = Integer.MAX_VALUE;
        int currentProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min,prices[i]);
            currentProfit = Math.max(currentProfit,prices[i]-min);
        }
        return currentProfit;
    }

    public int maxProfit(int[] prices) {
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
}
//leetcode submit region end(Prohibit modification and deletion)
