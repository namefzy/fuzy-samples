package com.fuzy.example.leetcode.editor.cn;
//给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
//
// 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。 
//
// 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。 
//
// 
//
// 示例 1: 
//
// 输入: [7,1,5,3,6,4]
//输出: 7
//解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
//     随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
// 
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
//解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。 
//
// 
//
// 提示： 
//
// 
// 1 <= prices.length <= 3 * 10 ^ 4 
// 0 <= prices[i] <= 10 ^ 4 
// 
// Related Topics 贪心算法 数组 
// 👍 830 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution36 {
    /**
     * [7, 1, 5, 6] 第二天买入，第四天卖出，收益最大（6-1），
     * 所以一般人可能会想，怎么判断不是第三天就卖出了呢? 这里就把问题复杂化了，
     * 根据题目的意思，当天卖出以后，当天还可以买入，所以其实可以第三天卖出，第三天买入，第四天又卖出（（5-1）+ （6-5） === 6 - 1）。
     * 所以算法可以直接简化为只要今天比昨天大，就卖出。
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int length = prices.length;
        int max = 0;
        for (int i = 0; i < length-1; i++) {
            if(prices[i]<prices[i+1]){
                max +=prices[i+1]-prices[i];
            }
        }
        return max;
    }

    private int res =0;
    public int maxProfit1(int[] prices){
        int length = prices.length;
        if(length<2){}
        return 0;
//        dfs(prices,0,length,0,res);
//        return res;
    }

    private void dfs(int[] prices, int index, int length, int status, int profit) {
        if(index==length){
            this.res = Math.max(this.res,profit);
            return;
        }
        dfs(prices, index + 1, length, status, profit);

        if (status == 0) {
            // 可以尝试转向 1
            dfs(prices, index + 1, length, 1, profit - prices[index]);

        } else {
            // 此时 status == 1，可以尝试转向 0
            dfs(prices, index + 1, length, 0, profit + prices[index]);
        }

    }


}
//leetcode submit region end(Prohibit modification and deletion)
