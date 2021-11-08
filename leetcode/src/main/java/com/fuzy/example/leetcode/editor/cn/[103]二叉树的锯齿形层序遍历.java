package com.fuzy.example.leetcode.editor.cn;//给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
//
// 例如： 
//给定二叉树 [3,9,20,null,null,15,7], 
//
// 
//    3
//   / \
//  9  20
//    /  \
//   15   7
// 
//
// 返回锯齿形层序遍历如下： 
//
// 
//[
//  [3],
//  [20,9],
//  [15,7]
//]
// 
// Related Topics 栈 树 广度优先搜索 
// 👍 368 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
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
class Solution135 {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        travel(root,res,0);
        return res;
    }

    private void travel(TreeNode root, List<List<Integer>> res, int level) {
        if(root==null){
            return;
        }
        //说明层数还没创建
        if(res.size()<=level){
            List<Integer> newLevel = new LinkedList<>();
            res.add(newLevel);
        }
        List<Integer> list = res.get(level);
        if(level%2==0){
            list.add(root.val);
        }else{
            list.add(0,root.val);
        }
        travel(root.left,res,level+1);
        travel(root.right,res,level+1);
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
