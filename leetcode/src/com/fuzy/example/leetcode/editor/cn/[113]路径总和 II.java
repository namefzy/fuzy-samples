package com.fuzy.example.leetcode.editor.cn;
//给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
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
//         /  \    / \
//        7    2  5   1
// 
//
// 返回: 
//
// [
//   [5,4,11,2],
//   [5,8,4,5]
//]
// 
// Related Topics 树 深度优先搜索 
// 👍 407 👎 0


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
 * TreeNode(int x) { val = x; }
 * }
 */
class Solution136 {
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    Deque<Integer> path = new LinkedList<Integer>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        dfs(root, sum);
        return ret;
    }


    public void dfs(TreeNode root,int sum){
        if(root==null){
            return;
        }
        path.offerLast(root.val);
        sum-=root.val;
        if(root.left==null&&root.right==null&&sum==0){
            ret.add(new LinkedList<>(path));
        }
        dfs(root.left,sum);
        dfs(root.right,sum);
        path.pollLast();

    }

    public static void main(String[] args) {
        Deque<Integer> path = new LinkedList<Integer>();
        path.offerLast(1);
        path.offerLast(2);
        System.out.println(path.pollLast());
        System.out.println(path.pollLast());
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
