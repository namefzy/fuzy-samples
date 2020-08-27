package com.fuzy.example.leetcode.editor.cn;
//给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
//
// 
//
// 在杨辉三角中，每个数是它左上方和右上方的数的和。 
//
// 示例: 
//
// 输入: 3
//输出: [1,3,3,1]
// 
//
// 进阶： 
//
// 你可以优化你的算法到 O(k) 空间复杂度吗？ 
// Related Topics 数组 
// 👍 171 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution35 {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> list = new ArrayList<>();
        return helper(0,rowIndex,list);
    }
    public List<Integer> helper(int startIndex,int rowIndex,List<Integer> oldList){
        List<Integer> newList = new ArrayList<>();
        if(startIndex==0){
            newList.add(1);
        }else{
            newList.add(1);
            for (int i = 0; i < oldList.size()-1; i++) {
                newList.add(oldList.get(i)+oldList.get(i+1));
            }
            newList.add(1);
        }
        if(startIndex==rowIndex){
            return newList;
        }
        return helper(startIndex+1,rowIndex,newList);
    }

}
//leetcode submit region end(Prohibit modification and deletion)
