package com.fuzy.example.leetcode.editor.cn;
//编写一个函数，以字符串作为输入，反转该字符串中的元音字母。
//
// 
//
// 示例 1： 
//
// 输入："hello"
//输出："holle"
// 
//
// 示例 2： 
//
// 输入："leetcode"
//输出："leotcede" 
//
// 
//
// 提示： 
//
// 
// 元音字母不包含字母 "y" 。 
// 
// Related Topics 双指针 字符串 
// 👍 148 👎 0


import java.util.Arrays;
import java.util.HashSet;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution345 {
    public String reverseVowels1(String s){
        int p1 = 0;
        int p2 = 1;
        char[] chars = s.toCharArray();
        while (p2<s.length()){
            //如果p1是元音字符，p2不是
            if(!vowels.contains(chars[p2])&&vowels.contains(chars[p1])){
                p2++;
            }else if(vowels.contains(chars[p2])&&!vowels.contains(chars[p1])){
                p1++;
            }else if(vowels.contains(chars[p2])&&vowels.contains(chars[p1])){
                if(p1==p2){
                    //交换
                    p2++;
                }else{
                    //交换
                    p1++;
                    p2++;
                }
            }
        }
        return s;
    }

    private final static HashSet<Character> vowels = new HashSet<>(
            Arrays.asList('a', 'e', 'i', 'o', 'u', 'A',
                    'E', 'I', 'O', 'U'));
    public String reverseVowels(String s) {
        // ArrayList<Character> arr = new ArrayList<>();
        // arr.add('a');
        // arr.add('e');
        // arr.add('i');
        // arr.add('o');
        // arr.add('u');
        // arr.add('A');
        // arr.add('E');
        // arr.add('I');
        // arr.add('O');
        // arr.add('U');
        if(s==null) {
            return null;
        }
        int low = 0,high = s.length()-1;
        char[] c = new char[s.length()] ;
        while(low<=high){
            char cl = s.charAt(low);
            char ch = s.charAt(high);
            if(!vowels.contains(cl)){
                c[low++] = cl;
            }else if(!vowels.contains(ch)){
                c[high--] = ch;
            }else{
                c[low++] = ch;
                c[high--] = cl;
            }
        }
        return new String(c);
    }

}
//leetcode submit region end(Prohibit modification and deletion)
