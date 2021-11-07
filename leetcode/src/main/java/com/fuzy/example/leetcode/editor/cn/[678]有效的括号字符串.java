package com.fuzy.example.leetcode.editor.cn;//给定一个只包含三种字符的字符串：（ ，） 和 *，写一个函数来检验这个字符串是否为有效字符串。有效字符串具有如下规则：
//
// 
// 任何左括号 ( 必须有相应的右括号 )。 
// 任何右括号 ) 必须有相应的左括号 ( 。 
// 左括号 ( 必须在对应的右括号之前 )。 
// * 可以被视为单个右括号 ) ，或单个左括号 ( ，或一个空字符串。 
// 一个空字符串也被视为有效字符串。 
// 
//
// 示例 1: 
//
// 
//输入: "()"
//输出: True
// 
//
// 示例 2: 
//
// 
//输入: "(*)"
//输出: True
// 
//
// 示例 3: 
//
// 
//输入: "(*))"
//输出: True
// 
//
// 注意: 
//
// 
// 字符串大小将在 [1，100] 范围内。 
// 
// Related Topics 栈 贪心 字符串 动态规划 👍 311 👎 0


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution678 {

    Map<Character,Character> map = new HashMap<>();


    public boolean checkValidString(String s) {

        map.put(')','(');
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        char c = s.charAt(0);
        if(c==')'){
            return false;
        }

        for (int i = 1; i < chars.length; i++) {
            char aChar = chars[i];
            if(aChar==' '){
                continue;
            }
            stack.push(aChar);
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
