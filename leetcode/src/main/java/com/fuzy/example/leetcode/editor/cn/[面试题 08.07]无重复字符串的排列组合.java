package com.fuzy.example.leetcode.editor.cn;//无重复字符串的排列组合。编写一种方法，计算某字符串的所有排列组合，字符串每个字符均不相同。
//
// 示例1: 
//
// 
// 输入：S = "qwe"
// 输出：["qwe", "qew", "wqe", "weq", "ewq", "eqw"]
// 
//
// 示例2: 
//
// 
// 输入：S = "ab"
// 输出：["ab", "ba"]
// 
//
// 提示: 
//
// 
// 字符都是英文字母。 
// 字符串长度在[1, 9]之间。 
// 
// Related Topics 回溯算法 
// 👍 32 👎 0
import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution92 {
    List<String> ans = new ArrayList<>();
    public String[] permutation(String S) {

        char[] chars = S.toCharArray();
        bfs(chars,0);
        String[] strings = new String[ans.size()];
        return ans.toArray(strings);
    }

    private void bfs(char[] chars, int first) {
        if(first==chars.length-1){
            ans.add(new String(chars));
            return;
        }
        /**
         * 解析对于每个i
         */
        for (int i = first; i < chars.length; i++) {
            //对于每次i!=first时，则将i的值与first值交换 也就是当前的首字母与第二个字符交互
            swap(chars,first,i);
            bfs(chars,first+1);
            //复原
            swap(chars,first,i);
        }
    }

    public void swap(char[] arr,int i,int j){
        char temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }

}
//leetcode submit region end(Prohibit modification and deletion)
