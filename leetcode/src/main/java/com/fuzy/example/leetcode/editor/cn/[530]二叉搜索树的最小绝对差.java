package com.fuzy.example.leetcode.editor.cn;
//给你一棵所有节点为非负值的二叉搜索树，请你计算树中任意两节点的差的绝对值的最小值。
//
// 
//
// 示例： 
//
// 输入：
//
//   1
//    \
//     3
//    /  \
//   2    5
//
//输出：
//1
//
//解释：
//最小绝对差为 1，其中 2 和 1 的差的绝对值为 1（或者 2 和 3）。
// 
//
// 
//
// 提示： 
//
// 
// 树中至少有 2 个节点。 
// 本题与 783 https://leetcode-cn.com/problems/minimum-distance-between-bst-nodes/ 
//相同 
// 
// Related Topics 树 
// 👍 132 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution31 {
    public int getMinimumDifference(TreeNode root) {
        int max = 0;
        int min = 0;

        TreeNode right = null;
        TreeNode left = null;
        while (root.right!=null||root.left!=null){
            right = root.right;
            left = root.left;
        }
        max = right==null?root.val:right.val;
        min = left==null?root.val:left.val;

        return max-min;
    }
    public int getMaxValue(TreeNode root){
        TreeNode right = root.right;
        if(right==null){
            return root.val;
        }
        return getMaxValue(right);
    }

     public class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
 }
}
//leetcode submit region end(Prohibit modification and deletion)
