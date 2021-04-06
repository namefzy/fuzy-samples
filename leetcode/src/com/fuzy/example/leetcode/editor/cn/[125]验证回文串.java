package com.fuzy.example.leetcode.editor.cn;
//给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
//
// 说明：本题中，我们将空字符串定义为有效的回文串。 
//
// 示例 1: 
//
// 输入: "A man, a plan, a canal: Panama"
//输出: true
// 
//
// 示例 2: 
//
// 输入: "race a car"
//输出: false
// 
// Related Topics 双指针 字符串 
// 👍 266 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution38 {
    public static void main(String[] args) {
        boolean palindrome = isPalindrome("race a car");
        System.out.println(palindrome);
    }

    public boolean isPalindrome1(String s){
        StringBuffer sgood = new StringBuffer();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                sgood.append(Character.toLowerCase(ch));
            }
        }
        int n = sgood.length();
        int left = 0;
        int right = n-1;
        char[] chars = sgood.toString().toCharArray();
        while(left<right){
            if(Character.toLowerCase(chars[left])!=Character.toLowerCase(chars[right])){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public  static boolean isPalindrome(String s) {
        StringBuffer sgood = new StringBuffer();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                sgood.append(Character.toLowerCase(ch));
            }
        }
        int n = sgood.length();

        for (int i = 0; i <= n/2; i++) {
            for (int j = n-i-1; j >=n/2; j--) {
                if(Character.toLowerCase(sgood.charAt(i))==Character.toLowerCase(sgood.charAt(j))){
                    if(i==j-1||i==j){
                        return true;
                    }
                    break;
                }else{
                    return false;
                }

            }
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
