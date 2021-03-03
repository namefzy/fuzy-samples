package com.fuzy.example.leetcode.editor.cn;//给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
//
// 假定 BST 有如下定义： 
//
// 
// 结点左子树中所含结点的值小于等于当前结点的值 
// 结点右子树中所含结点的值大于等于当前结点的值 
// 左子树和右子树都是二叉搜索树 
// 
//
// 例如： 
//给定 BST [1,null,2,2], 
//
//    1
//    \
//     2
//    /
//   2
// 
//
// 返回[2]. 
//
// 提示：如果众数超过1个，不需考虑输出顺序 
//
// 进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内） 
// Related Topics 树 
// 👍 265 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
class Solution160 {

    public int[] findMode(TreeNode root) {
        Map<Integer,Integer> map = new HashMap<>();
        helper(root,map);

        /**
         * 求map中value最大的值,可能存在多个相同的众数 []
         */
        int maxCount = 0;
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            //如果统计的值大于count
            if(value>maxCount){
                maxCount = value;
                list.clear();
                list.add(key);
            }else if(value == maxCount){
                //如果等于最大值
                list.add(key);
            }
        }
        int[] arrays = new int[list.size()];
        for (int j = 0; j < list.size(); j++) {
            arrays[j] = list.get(j);
        }
        return arrays;
    }

    private void helper(TreeNode root, Map<Integer,Integer> map) {
        if(root==null){
            return;
        }
        if(map.containsKey(root.val)){
            map.put(root.val,map.get(root.val)+1);
        }else{
            map.put(root.val,1);
        }
        helper(root.left,map);
        helper(root.right,map);
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
