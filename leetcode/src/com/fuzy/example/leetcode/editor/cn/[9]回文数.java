package com.fuzy.example.leetcode.editor.cn;//判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
//
// 示例 1: 
//
// 输入: 121
//输出: true
// 
//
// 示例 2: 
//
// 输入: -121
//输出: false
//解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
// 
//
// 示例 3: 
//
// 输入: 10
//输出: false
//解释: 从右向左读, 为 01 。因此它不是一个回文数。
// 
//
// 进阶: 
//
// 你能不将整数转为字符串来解决这个问题吗？ 
// Related Topics 数学 
// 👍 1179 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution01 {
    public static void main(String[] args) {

        String s = "hello";
        String s1 = "ll";
        int i = s.indexOf(s1);
        System.out.println(i);
    }
    public static boolean isPalindrome2(int x) {
        long startTime = System.currentTimeMillis();
        if (x < 0) {
            return false;
        }
        int div = 1;
        //取最高位是几个0
        while (x/div>=10){
            div *=10;
        }
        while (x>0){
            int left = x/div;
            int right = x%10;
            if(left!=right){
                return false;
            }

            x = (x%div)/10;
            div = div/100;
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
        return true;
    }
    public static boolean isPalindrome1(int x) {
        long startTime = System.currentTimeMillis();
        int y = x;
        if(x<0){
            return false;
        }
        int res = 0;
        int newRes = 0;
        while (x!=0){
            res = x%10;
            newRes = newRes*10+res;
            if(newRes%10!=res){
                return false;
            }
            x = x/10;
        }
        if(y!=newRes){
            return false;
        }

        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
        return true;
    }
    /**
     *@Description 1563847412：反转小于最大整数；-2147483648：取绝对值超过最大整数
     *@Param
     *@Return
     *@Author fuzy
     *@Date 2020/7/18
     */
    public static int reverse(int x) {
        int res = 0;
        int newRes = 0;
        while (x!=0){
            res = x%10;
            newRes = newRes*10+res;
            if(newRes%10!=res){
                return 0;
            }
            x = x/10;
        }
        return newRes;
        //思路一作死
//        int y;
//        if(x==-2147483648){
//            y = Math.abs(x+1);
//        }else{
//            y = Math.abs(x);
//        }
//
//        String s = String.valueOf(y);
//        int length = s.length();
//        int num = 0;
//        for (int i = 0; i < length; i++) {
//            int res = num;
//            num =res+ (y/cifang(length-i))*cifang(i+1);
//            if(y/cifang(length-i)>2&&cifang(i+1)==1000000000){
//                return 0;
//
//            }else if((num-(y/cifang(length-i))*cifang(i+1))!=res){
//                return 0;
//            }
//            y -= (y/cifang(length-i))*cifang(length-i);
//        }
//        if(x<0){
//            num = Integer.valueOf("-"+num);
//        }
//        return num;
    }

    public static int cifang(int length){
        int num = 1;
        for (int i = 0; i < length; i++) {
            if(i==0){
                num =1;
            }else {
                num *=10;
            }
        }
        return num;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
