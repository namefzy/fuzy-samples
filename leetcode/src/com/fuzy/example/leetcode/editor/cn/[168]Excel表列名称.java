package com.fuzy.example.leetcode.editor.cn;//给定一个正整数，返回它在 Excel 表中相对应的列名称。
//
// 例如， 
//
//     1 -> A
//    2 -> B
//    3 -> C
//    ...
//    26 -> Z
//    27 -> AA
//    28 -> AB 
//    ...
// 
//
// 示例 1: 
//
// 输入: 1
//输出: "A"
// 
//
// 示例 2: 
//
// 输入: 28
//输出: "AB"
// 
//
// 示例 3: 
//
// 输入: 701
//输出: "ZY"
// 
// Related Topics 数学 
// 👍 246 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution17 {
    public static void main(String[] args) {
        String s = String.valueOf('a' + 1);
        System.out.println(String.valueOf(s));
        System.out.println('a');
    }
    /**
     * A 1
     * B 2
     * AB 27
     * ZY 701 26*26+25
     * ZZY 26*26*26+25
     * @param n
     * @return
     */
    public String convertToTitle(int n) {
        Map<Integer,String> map = new HashMap<>();
        for (int i = 0; i <=26; i++) {
            if(i==0){
                map.put(0,"");
            }else{
                map.put(i, String.valueOf((char) ('A' + i-1)));
            }
        }
        StringBuilder sb = new StringBuilder();
        while (n>0){
            int i = n % 26;
            if(i==0){
                i =26;
                n -=1;
            }
            sb.insert(0,map.get(i));
            //如果z,zz,zzz,zzzz....怎么解决 第一位是z,但是这里的结果却是a
            n = n/26;

        }


        return sb.toString();
    }

}
//leetcode submit region end(Prohibit modification and deletion)
