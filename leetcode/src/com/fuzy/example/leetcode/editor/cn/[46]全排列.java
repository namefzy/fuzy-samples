package com.fuzy.example.leetcode.editor.cn;
//给定一个 没有重复 数字的序列，返回其所有可能的全排列。
//
// 示例: 
//
// 输入: [1,2,3]
//输出:
//[
//  [1,2,3],
//  [1,3,2],
//  [2,1,3],
//  [2,3,1],
//  [3,1,2],
//  [3,2,1]
//] 
// Related Topics 回溯算法 
// 👍 1027 👎 0


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution103 {
    public static void main(String[] args) {
        permute(new int[]{1,2,3});
    }
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        for (int num : nums) {
            output.add(num);
        }
        backtrack(nums.length,output,res,0);
        return res;
    }

    public static void backtrack(int n, List<Integer> output, List<List<Integer>> res, int first) {
        if(first==n){
            res.add(output);
            return;
        }
        //for循环代表每种组合开头数字
        for (int i = first; i < n; i++) {
            System.out.println("交换前：output="+output.toString());
            Collections.swap(output,first,i);
            System.out.println("交换中：output="+output.toString());
            //
            backtrack(n,output,res,first+1);
            Collections.swap(output,first,i);
            System.out.println("交换后：output="+output.toString());
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)
