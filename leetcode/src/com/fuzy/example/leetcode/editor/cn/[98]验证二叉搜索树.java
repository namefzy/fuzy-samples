package com.fuzy.example.leetcode.editor.cn;//给定一个二叉树，判断其是否是一个有效的二叉搜索树。
//
// 假设一个二叉搜索树具有如下特征： 
//
// 
// 节点的左子树只包含小于当前节点的数。 
// 节点的右子树只包含大于当前节点的数。 
// 所有左子树和右子树自身必须也是二叉搜索树。 
// 
//
// 示例 1: 
//
// 输入:
//    2
//   / \
//  1   3
//输出: true
// 
//
// 示例 2: 
//
// 输入:
//    5
//   / \
//  1   4
//     / \
//    3   6
//输出: false
//解释: 输入为: [5,1,4,null,null,3,6]。
//     根节点的值为 5 ，但是其右子节点值为 4 。
// 
// Related Topics 树 深度优先搜索 递归 
// 👍 858 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution109 {
    public boolean isValidBST(TreeNode root) {
         return helper(root,null,null);
    }



    public boolean isValidBST1(TreeNode root){
        List<String> list = new ArrayList<>();

        Deque<TreeNode> stack = new LinkedList<>();
        double inorder = -Double.MAX_VALUE;
        while (!stack.isEmpty()||root!=null){
            while (root!=null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(root.val<=inorder){
                return false;
            }
            inorder = root.val;
            root = root.right;
        }
        return true;
    }

    public boolean helper(TreeNode root,Integer lower,Integer upper){
        if(root==null){
            return true;
        }
        int val = root.val;
        if(lower!=null&&val<=lower){
            return false;
        }
        if(upper!=null&&val>=upper){
            return false;
        }
        if(!helper(root.left,val,upper)){
            return false;
        }
        if(!helper(root.right,val,upper)){
            return false;
        }
        return true;
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
