package com.fuzy.example.leetcode.editor.cn;//给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
//
// 示例 1: 
//
// 
//输入: "Let's take LeetCode contest"
//输出: "s'teL ekat edoCteeL tsetnoc" 
// 
//
// 注意：在字符串中，每个单词由单个空格分隔，并且字符串中不会有任何额外的空格。 
// Related Topics 字符串 
// 👍 212 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution29 {
    public String reverseWords(String s) {
        String[] arr = s.split("-");
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<arr.length;i++){
            sb.append(new StringBuilder(arr[i]).reverse());
            if(i!=arr.length-1){
                sb.append(" ");
            }
        }
        return sb.toString();
    }
    public String[] split(String s){
        List<String> list = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        for (int i=0;i<s.length();i++){
            if(s.charAt(i)==' '){
                list.add(word.toString());
                word = new StringBuilder();
            }else{
                word.append(s.charAt(i));
            }
        }
        return null;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
