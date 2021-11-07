package com.fuzy.example.leetcode.editor.cn;
//给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
//
// 
//
// 在杨辉三角中，每个数是它左上方和右上方的数的和。 
//
// 示例: 
//
// 输入: 5
//输出:
//[
//     [1],
//    [1,1],
//   [1,2,1],
//  [1,3,3,1],
// [1,4,6,4,1]
//] 
// Related Topics 数组 
// 👍 340 👎 0


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution34 {

    public List<List<Integer>> generate1(int numRows) {
        List<List<Integer>> res = new LinkedList<>();
        helper1(res, new ArrayList<>(), numRows, 0);
        return res;
    }

    /**
     * 分析，对于每一层都会新建一个list,该list通过上一层的list计算而来
     * @param res
     * @param list
     * @param numRows
     * @param curr
     */
    public void helper1(List<List<Integer>> res, List<Integer> list, int numRows, int curr){
        //对于每一层都有上一
        if (curr >= numRows) {
            return ;
        }
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            if(i==0||i==numRows-1){
                list1.add(1);
            }else{
                list1.add(list.get(i-1)+list.get(i));
            }

        }
        res.add(list1);
        helper(res,list1,numRows,++curr);
    }


    public static void main(String[] args) {
        List<List<Integer>> generate = generate(5);
        System.out.println(generate);
    }
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> oldList = new ArrayList<>();
        return helper(list,oldList,1,numRows);
    }
    public static  List<List<Integer>> helper(List<List<Integer>> list,List<Integer> oldList,int startRows,int numRows){
        List<Integer> newList = new ArrayList<>(startRows);

        if(startRows==1){
            newList.add(1);
            list.add(newList);
        }else if(startRows==2){
            newList.add(1);
            newList.add(1);
            list.add(newList);
        }else {
            newList.add(1);
            for (int i = 0; i < oldList.size()-1; i++) {
                newList.add(oldList.get(i)+oldList.get(i+1));
            }
            newList.add(1);
            list.add(newList);
        }
        if(startRows==numRows){
            return list;
        }
        return helper(list,newList,startRows+1,numRows);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
