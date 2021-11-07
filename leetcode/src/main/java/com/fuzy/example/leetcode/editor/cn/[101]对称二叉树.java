package com.fuzy.example.leetcode.editor.cn;
//给定一个二叉树，检查它是否是镜像对称的。
//
// 
//
// 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。 
//
//     1
//   / \
//  2   2
// / \ / \
//3  4 4  3
// 
//
// 
//
// 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的: 
//
//     1
//   / \
//  2   2
//   \   \
//   3    3
// 
//
// 
//
// 进阶： 
//
// 你可以运用递归和迭代两种方法解决这个问题吗？ 
// Related Topics 树 深度优先搜索 广度优先搜索 
// 👍 944 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.LinkedList;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution15 {
    public boolean isSymmetric1(TreeNode root) {
        TreeNode left = root.left;
        TreeNode right = root.right;
        while (left!=null||right!=null){
            if(left!=null&&right!=null){
                if(left.val!=right.val){
                    return false;
                }
                left = left.left;
                right = right.right;
            }else{
                return false;
            }
        }
        return true;
    }

    public boolean check(TreeNode u,TreeNode v){
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(u);
        q.offer(v);
        while (!q.isEmpty()){
            u = q.poll();
            v = q.poll();
            if(u==null&&v==null){
                continue;
            }
            if((u==null||v==null)||(u.val!=v.val)){
                return false;
            }
            q.offer(u.left);
            q.offer(v.right);

            q.offer(u.right);
            q.offer(v.left);

        }

        return true;
    }

    public boolean isSymmetric(TreeNode root) {

        if (root==null){
            return true;
        }
        return digui(root.left,root.right);
    }

    public boolean digui(TreeNode left,TreeNode right){
        if(left==null&&right==null){
            return true;
        }
        if(left==null||right==null){
            return false;
        }
        if(left.val==right.val){
            return true;
        }
        return digui(left.left,right.right)&&digui(left.right,right.left);
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
