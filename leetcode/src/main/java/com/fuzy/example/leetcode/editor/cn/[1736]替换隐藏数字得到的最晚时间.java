package com.fuzy.example.leetcode.editor.cn;//给你一个字符串 time ，格式为 hh:mm（小时：分钟），其中某几位数字被隐藏（用 ? 表示）。
//
// 有效的时间为 00:00 到 23:59 之间的所有时间，包括 00:00 和 23:59 。 
//
// 替换 time 中隐藏的数字，返回你可以得到的最晚有效时间。 
//
// 
//
// 示例 1： 
//
// 
//输入：time = "2?:?0"
//输出："23:50"
//解释：以数字 '2' 开头的最晚一小时是 23 ，以 '0' 结尾的最晚一分钟是 50 。
// 
//
// 示例 2： 
//
// 
//输入：time = "0?:3?"
//输出："09:39"
// 
//
// 示例 3： 
//
// 
//输入：time = "1?:22"
//输出："19:22"
// 
//
// 
//
// 提示： 
//
// 
// time 的格式为 hh:mm 
// 题目数据保证你可以由输入的字符串生成有效的时间 
// 
// Related Topics 字符串 
// 👍 39 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution1736 {
    public String maximumTime(String time) {
        if(time.length()!=5){
            throw new RuntimeException("时间不符合正确格式");
        }
        char[] chars = time.toCharArray();
        for (int i = 0; i < chars.length; i++) {

            if(chars[i]=='?'){
                if(i==0){
                    if(chars[i+1]-'0'<=3||chars[i+1]=='?'){
                        chars[i] = '2';
                    }else{
                        chars[i] = '1';
                    }
                }else if(i==1){
                    if(chars[0]=='0'){
                        chars[i] = '9';
                    }else if(chars[0]=='1'){
                        chars[i] = '9';
                    }else{
                        chars[i] = '3';
                    }
                }else if(i==3){
                    chars[i] = '5' ;
                }else if(i==4){
                    chars[i] = '9';
                }
            }

        }
        return new String(chars);
    }

    public static void main(String[] args) {
        int i = '3' - '0';
        System.out.println(i==3);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
