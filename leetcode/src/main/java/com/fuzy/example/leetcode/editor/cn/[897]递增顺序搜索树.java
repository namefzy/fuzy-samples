package com.fuzy.example.leetcode.editor.cn;//给你一棵二叉搜索树，请你 按中序遍历 将其重新排列为一棵递增顺序搜索树，使树中最左边的节点成为树的根节点，并且每个节点没有左子节点，只有一个右子节点。
//
// 
//
// 示例 1： 
//
// 
//输入：root = [5,3,6,2,4,null,8,1,null,null,null,7,9]
//输出：[1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]
// 
//
// 示例 2： 
//
// 
//输入：root = [5,1,7]
//输出：[1,null,5,null,7]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数的取值范围是 [1, 100] 
// 0 <= Node.val <= 1000 
// 
// Related Topics 树 深度优先搜索 递归 
// 👍 153 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution897 {
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        dfs(list,root);

        TreeNode sentinel = new TreeNode(),head = sentinel;
        for (Integer integer : list) {
            sentinel.right = new TreeNode(integer);
            sentinel = sentinel.right;
        }
        return sentinel.right;
    }

    /**
     * 递归实现
     *
     * @param list
     * @param root
     * @return
     */
    private void dfs(List<Integer> list, TreeNode root){
        if(root==null){
            return;
        }
        dfs(list, root.left);
        list.add(root.val);
        dfs(list,root.right);
    }

     public class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode() {}
     TreeNode(int val) { this.val = val; }
     TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
         this.left = left;
         this.right = right;
     }
 }
}
//leetcode submit region end(Prohibit modification and deletion)
