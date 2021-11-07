package com.fuzy.example.leetcode.editor.cn;//给定一个仅包含大小写字母和空格 ' ' 的字符串 s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
//
// 如果不存在最后一个单词，请返回 0 。 
//
// 说明：一个单词是指仅由字母组成、不包含任何空格字符的 最大子字符串。 
//
// 
//
// 示例: 
//
// 输入: "Hello World"
//输出: 5
// 
// Related Topics 字符串 
// 👍 225 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution4 {

    public static void main(String[] args) {
        int length = lengthOfLastWord1(" ");
        System.out.println(length);
    }

    /**
     *  'a' ' ' '' 'a ' ' a'
     * @param s
     * @return
     */
    public static int lengthOfLastWord1(String s){

        int end = s.length()-1;
        while (end>=0&&s.charAt(end)==' '){
            end--;
        }
        if(end<0){
            return 0;
        }
        int start = end;
        while (start>=0&&s.charAt(start)!=' '){
            start --;
        }

        return end-start;
    }
    public int lengthOfLastWord(String s) {
        if(s.trim().length()==0){
            return 0;
        }
        String[] s1 = s.split(" ");
        String s2 = s1[s1.length - 1];
        return s2.length();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
