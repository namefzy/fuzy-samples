package com.fuzy.example.leetcode.editor.cn;//输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student. "，
//则输出"student. a am I"。 
//
// 
//
// 示例 1： 
//
// 输入: "the sky is blue"
//输出: "blue is sky the"
// 
//
// 示例 2： 
//
// 输入: "  hello world!  "
//输出: "world! hello"
//解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
// 
//
// 示例 3： 
//
// 输入: "a good   example"
//输出: "example good a"
//解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
// 
//
// 
//
// 说明： 
//
// 
// 无空格字符构成一个单词。 
// 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。 
// 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。 
// 
//
// 注意：本题与主站 151 题相同：https://leetcode-cn.com/problems/reverse-words-in-a-string/ 
//
//
// 注意：此题对比原题有改动 
// Related Topics 双指针 字符串 
// 👍 125 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionOffer5801 {
    public String reverseWords(String s) {
        s = s.trim();
        String[] words = s.split(" ");
        StringBuilder  sb = new StringBuilder();
        for (int i = words.length-1; i >=0 ; i--) {
            if(!" ".equals(words[i])){
                if(i==0){
                    sb.append(words[i]);
                }else {
                    sb.append(words[i]).append(" ");
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "  hello world!  ";
        String trim = s.trim();
        System.out.println(trim);
        String[] s1 = s.split(" ");
        System.out.println(s1.length);
        for (String s2 : s1) {
            System.out.println(s2);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s1.length; i++) {
            sb.append(s1[i]).append(" ");
        }
        System.out.println(sb.toString());
    }
}
//leetcode submit region end(Prohibit modification and deletion)
