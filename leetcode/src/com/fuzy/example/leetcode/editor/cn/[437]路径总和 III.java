package com.fuzy.example.leetcode.editor.cn;
//给定一个二叉树，它的每个结点都存放着一个整数值。
//
// 找出路径和等于给定数值的路径总数。 
//
// 路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。 
//
// 二叉树不超过1000个节点，且节点数值范围是 [-1000000,1000000] 的整数。 
//
// 示例： 
//
// root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
//
//      10
//     /  \
//    5   -3
//   / \    \
//  3   2   11
// / \   \
//3  -2   1
//
//返回 3。和等于 8 的路径有:
//
//1.  5 -> 3
//2.  5 -> 2 -> 1
//3.  -3 -> 11
// 
// Related Topics 树 
// 👍 789 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.HashMap;
import java.util.Map;

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
class Solution437 {

    Map<Integer, Integer> prefixMap;
    int target;

    public int pathSum(TreeNode root, int sum) {
        prefixMap = new HashMap<>();
        target = sum;

        prefixMap.put(0, 1);
        return recur(root, 0);
    }

    private int recur(TreeNode node, int curSum) {
        if(node == null) {
            return 0;
        }

        int res = 0;
        curSum += node.val;

        res += prefixMap.getOrDefault(curSum - target, 0);
        prefixMap.put(curSum, prefixMap.getOrDefault(curSum, 0) + 1);

        int left = recur(node.left, curSum);
        int right = recur(node.right, curSum);

        res = res + left + right;

        prefixMap.put(curSum, prefixMap.get(curSum) - 1);

        return res;
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
