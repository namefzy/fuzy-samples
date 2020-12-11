package com.fuzy.example.leetcode.editor.cn;
//计算给定二叉树的所有左叶子之和。
//
// 示例： 
//
// 
//    3
//   / \
//  9  20
//    /  \
//   15   7
//
//在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24 
//
// 
// Related Topics 树 
// 👍 253 👎 0


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
class Solution88 {
    public int sumOfLeftLeaves(TreeNode root) {
        //结束条件
        if(root==null){
            return 0;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        if(left==null){
            return 0;
        }
        int a = sumOfLeftLeaves(left);
        int b = sumOfLeftLeaves(right);
        return a+b;
    }
    public class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
 }
}
//leetcode submit region end(Prohibit modification and deletion)
