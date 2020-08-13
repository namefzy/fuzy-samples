package com.fuzy.example.leetcode.editor.cn;
//猜数字游戏的规则如下：
//
// 
// 每轮游戏，系统都会从 1 到 n 随机选择一个数字。 请你猜选出的是哪个数字。 
// 如果你猜错了，系统会告诉你这个数字比系统选出的数字是大了还是小了。 
// 
//
// 你可以通过调用一个预先定义好的接口 guess(int num) 来获取猜测结果，返回值一共有 3 种可能的情况（-1，1 或 0）： 
//
// -1 : 系统选出的数字比你猜测的数字小
// 1 : 系统选出的数字比你猜测的数字大
// 0 : 恭喜！你猜对了！
// 
//
// 
//
// 示例 : 
//
// 输入: n = 10, pick = 6
//输出: 6 
// Related Topics 二分查找 
// 👍 76 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/** 
 * Forward declaration of guess API.
 * @return 	     -1 if num is lower than the guess number
 *			      1 if num is higher than the guess number
 *               otherwise return 0
 * int guess(int num);
 */

class Solution22 extends GuessGame {
    // -1 : 系统选出的数字比你猜测的数字小
// 1 : 系统选出的数字比你猜测的数字大
// 0 : 恭喜！你猜对了！
    public int guessNumber(int n) {
        int left = 1;
        int right = n;
        while (left<right){
            int mid = (left+right)>>>1;
            if(guess(mid)==-1) {
                right = mid-1;
            }else if(guess(mid)==1){
                left = mid+1;
            }else{
                return mid;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        int i = 11 >>> 2;
        System.out.println(i);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
