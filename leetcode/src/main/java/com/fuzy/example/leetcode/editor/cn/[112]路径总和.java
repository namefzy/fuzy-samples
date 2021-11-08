package com.fuzy.example.leetcode.editor.cn;
//给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
//
// 说明: 叶子节点是指没有子节点的节点。 
//
// 示例: 
//给定如下二叉树，以及目标和 sum = 22， 
//
//               5
//             / \
//            4   8
//           /   / \
//          11  13  4
//         /  \      \
//        7    2      1
// 
//
// 返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2。 
// Related Topics 树 深度优先搜索 
// 👍 461 👎 0


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
class Solution84 {
    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(5);
        treeNode.left = new TreeNode(3);
        hasPathSum(treeNode,5);
    }
    public static boolean hasPathSum(TreeNode root, int sum) {
        if(root==null){
            return false;
        }
        TreeNode p1 = root.left;
        TreeNode p2 = root.right;
        if(p2==null&&p1==null){
            return sum == root.val;
        }
        boolean left = hasPathSum(p1, sum - root.val);
        boolean right = hasPathSum(p2, sum - root.val);

        return left||right;
    }
     public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
