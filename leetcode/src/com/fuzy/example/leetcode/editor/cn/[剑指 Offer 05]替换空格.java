package com.fuzy.example.leetcode.editor.cn;
//请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
//
// 
//
// 示例 1： 
//
// 输入：s = "We are happy."
//输出："We%20are%20happy." 
//
// 
//
// 限制： 
//
// 0 <= s 的长度 <= 10000 
// 👍 56 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution132 {
    public String replaceSpace(String s) {
        int length = s.length();
        char[] array = new char[length*3];
        int size = 0;
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                array[size++] = '%';
                array[size++] = '2';
                array[size++] = '0';
            } else {
                array[size++] = c;
            }

        }
        String newStr = new String(array,0,size);
        return newStr;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
