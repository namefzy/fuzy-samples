package com.fuzy.example.leetcode.editor.cn;
//给定一个字符串 s 和一个整数 k，从字符串开头算起，每 2k 个字符反转前 k 个字符。
//
// 
// 如果剩余字符少于 k 个，则将剩余字符全部反转。 
// 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "abcdefg", k = 2
//输出："bacdfeg"
// 
//
// 示例 2： 
//
// 
//输入：s = "abcd", k = 2
//输出："bacd"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 104 
// s 仅由小写英文组成 
// 1 <= k <= 104 
// 
// Related Topics 双指针 字符串 
// 👍 145 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution541 {

    public static   void main(String[] args) {
        String abcdefg = reverseStr("abcdefg", 1);
        System.out.println(abcdefg);
    }

    public static String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        int res = chars.length/(2*k);
        int mod = chars.length%(2*k);
        for (int i = 0; i < res;i++) {
            int range = (i+1)*(2*k);
            reverse(chars,i*(2*k),range-k-1);
        }
        if(mod<k){
            reverse(chars,res*(2*k),res*(2*k)+mod-1);
        }
        if(mod>=k&&mod<2*k){
            reverse(chars,res*(2*k),res*(2*k)+k-1);
        }
        return new String(chars);
    }
    private static void reverse(char[] chars,int left, int right){
        while (left<=right){
            char tmp = chars[right];
            chars[right] = chars[left];
            chars[left] = tmp;
            left++;
            right--;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
