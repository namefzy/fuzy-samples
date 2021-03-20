package com.fuzy.example.leetcode.editor.cn;//给定一棵二叉树，设计一个算法，创建含有某一深度上所有节点的链表（比如，若一棵树的深度为 D，则会创建出 D 个链表）。返回一个包含所有深度的链表的数组。
//
// 
//
// 示例： 
//
// 输入：[1,2,3,4,5,null,7,8]
//
//        1
//       /  \ 
//      2    3
//     / \    \ 
//    4   5    7
//   /
//  8
//
//输出：[[1],[2,3],[4,5,7],[8]]
// 
// Related Topics 树 广度优先搜索 
// 👍 45 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution0403 {
    public ListNode[] listOfDepth(TreeNode tree) {
        //计算树的最大深度
        dfs(tree,0);
        return null;
    }

    /**
     * 计算树的最大深度
     * @param tree
     * @param index
     */
    private int  dfs(TreeNode tree, int index) {
        if(tree==null){
            return index;
        }
        int left = dfs(tree.left,index+1);
        int right = dfs(tree.right,index+1);
        return Math.min(left,right);
    }

    public class TreeNode{
        private TreeNode left;
        private TreeNode right;
        private int val;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
