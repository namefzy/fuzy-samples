package com.fuzy.example.leetcode.editor.cn;
//编写一个程序判断给定的数是否为丑数。
//
// 丑数就是只包含质因数 2, 3, 5 的正整数。 
//
// 示例 1: 
//
// 输入: 6
//输出: true
//解释: 6 = 2 × 3 
//
// 示例 2: 
//
// 输入: 8
//输出: true
//解释: 8 = 2 × 2 × 2
// 
//
// 示例 3: 
//
// 输入: 14
//输出: false 
//解释: 14 不是丑数，因为它包含了另外一个质因数 7。 
//
// 说明： 
//
// 
// 1 是丑数。 
// 输入不会超过 32 位有符号整数的范围: [−231, 231 − 1]。 
// 
// Related Topics 数学 
// 👍 145 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution19 {
    public boolean isUgly(int num) {
        if(num==2||num==3||num==5){
            return true;
        }
        int ans = num;
        int mod = 0;
        while (mod==0){
            mod = ans%2;
            if(mod==0&&ans!=2){
                ans = ans/2;
            }else {
                break;
            }
        }
        if(ans == 2){
            return true;
        }
        mod = 0;
        while (mod==0){
            mod=ans%3;
            if(mod==0&&ans!=3){
                ans = ans/3;
            }else {
                break;
            }
        }
        if(ans == 3){
            return true;
        }
        mod = 0;
        while (mod==0){
            mod=ans%5;
            if(mod==0&&ans!=5){
                ans = ans/5;
            }else {
                break;
            }
        }
        if(ans==5){
            return true;
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
