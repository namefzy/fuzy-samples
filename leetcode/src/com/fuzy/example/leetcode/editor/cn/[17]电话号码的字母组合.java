package com.fuzy.example.leetcode.editor.cn;//给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
//
// 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。 
//
// 
//
// 示例: 
//
// 输入："23"
//输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
// 
//
// 说明: 
//尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序。 
// Related Topics 深度优先搜索 递归 字符串 回溯算法 
// 👍 1048 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution119 {
    //最终输出结果的list
    List<String> res = new ArrayList<>();
    String[] letter_map = {" ","*","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    public List<String> letterCombinations(String digits) {
        //注意边界条件
        if(digits==null || digits.length()==0) {
            return new ArrayList<>();
        }
        iterStr(digits, new StringBuilder(), 0);
        return res;
    }

    /**
     *
     * @param str 23
     * @param letter 存储的结果集 比如 ad ae af
     * @param index
     */
    private void iterStr(String str, StringBuilder letter, int index) {
        if(index==str.length()){
            res.add(letter.toString());
            return;
        }
        char c = str.charAt(index);
        int pos = c - '0';
        String mapString = letter_map[pos];
        for (int i = 0; i < mapString.length(); i++) {
            //添加第一个元素
            letter.append(mapString.charAt(i));
            iterStr(str,letter,index+1);
            //在一个循环中删除添加的第一个元素，保证StringBuilder没有多余其他值
            letter.deleteCharAt(letter.length()-1);
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)
