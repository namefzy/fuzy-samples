package com.fuzy.example.leetcode.editor.cn;//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
//
// 有效括号组合需满足：左括号必须以正确的顺序闭合。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3
//输出：["((()))","(()())","(())()","()(())","()()()"]
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：["()"]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 8 
// 
// Related Topics 字符串 动态规划 回溯 👍 2119 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution022 {
    public List<String> generateParenthesis(int n){
        List<String> res = new ArrayList<>();
        if(n==0){
            return res;
        }
        dfs("",0,0,n,res);
        return res;
    }

    private void dfs(String s, int left, int right, int n, List<String> res) {
        if(left==n&&right==n){
            res.add(s);
            return;
        }
        if(left<right){
            return;
        }
        if (left < n) {
            dfs(s + "(", left + 1, right, n, res);
        }
        if (right < n) {
            dfs(s + ")", left, right + 1, n, res);
        }

    }

    public List<String> generateParenthesis1(int n) {
        List<String> combinations = new ArrayList<>();
        generateAll(new char[2*n],0,combinations);
        return combinations;
    }

    private void generateAll(char[] chars, int pos, List<String> combinations) {
        if(pos==chars.length){
            if(valid(chars)){
                combinations.add(new String(chars));
            }
        }else{
            chars[pos] = '(';
            generateAll(chars,pos+1,combinations);
            chars[pos] = ')';
            generateAll(chars,pos+1,combinations);
        }

    }

    public boolean valid(char[] chars){
        int balance = 0 ;
        for (char aChar : chars) {
            if(aChar=='('){
                balance++;
            }else{
                balance--;
            }
            if (balance < 0) {
                return false;
            }
        }
        return balance==0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
