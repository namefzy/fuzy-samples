package com.fuzy.example.leetcode.editor.cn;
//将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
//
// 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。 
//
// 示例: 
//
// 给定有序数组: [-10,-3,0,5,9],
//
//一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
//
//      0
//     / \
//   -3   9
//   /   /
// -10  5
// 
// Related Topics 树 深度优先搜索 
// 👍 561 👎 0


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
class Solution33 {
    /**
     * 左节点 取小区间的值
     * 右节点 取大区间的值
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(nums,0,nums.length);
    }
    public TreeNode helper(int[] nums, int left, int right) {
        if(left>=right){
            return null;
        }
        int mid = (left+right)/2;
        TreeNode treeNode = new TreeNode(nums[mid]);
        treeNode.left = helper(nums,left,mid);
        treeNode.right = helper(nums,mid+1,right);
        return treeNode;
    }
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
}
//leetcode submit region end(Prohibit modification and deletion)
