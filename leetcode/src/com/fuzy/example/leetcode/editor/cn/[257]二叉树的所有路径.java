package com.fuzy.example.leetcode.editor.cn;//给定一个二叉树，返回所有从根节点到叶子节点的路径。
import com.sun.org.apache.bcel.internal.generic.AllocationInstruction;

import java.util.*;
//
// 说明: 叶子节点是指没有子节点的节点。 
//
// 示例: 
//
// 输入:
//
//   1
// /   \
//2     3
// \
//  5
//
//输出: ["1->2->5", "1->3"]
//
//解释: 所有根节点到叶子节点的路径为: 1->2->5, 1->3 
// Related Topics 树 深度优先搜索 
// 👍 397 👎 0


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
class Solution87 {
    public List<String> binaryTreePaths(TreeNode root) {

        List<String> list = new ArrayList<>();
        getNodesPath(root,list,"");

        return list;
    }

    public void getNodesPath(TreeNode root,List<String> list,String path){
        if(root!=null){
            StringBuffer sb = new StringBuffer(path);
            sb.append(root.val);
            if(root.left==null&&root.right==null){
                list.add(sb.toString());
            }else{
                sb.append("->");
                getNodesPath(root.left,list,sb.toString());
                getNodesPath(root.right,list,sb.toString());
            }
        }
    }

     public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
}
//leetcode submit region end(Prohibit modification and deletion)
