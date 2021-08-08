package com.fuzy.example.leetcode.editor.cn;//输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
//
// B是A的子结构， 即 A中有出现和B相同的结构和节点值。 
//
// 例如: 
//给定的树 A: 
//
// 3 
// / \ 
// 4 5 
// / \ 
// 1 2 
//给定的树 B： 
//
// 4 
// / 
// 1 
//返回 true，因为 B 与 A 的一个子树拥有相同的结构和节点值。 
//
// 示例 1： 
//
// 输入：A = [1,2,3], B = [3,1]
//输出：false
// 
//
// 示例 2： 
//
// 输入：A = [3,4,5,1,2], B = [4,1]
//输出：true 
//
// 限制： 
//
// 0 <= 节点个数 <= 10000 
// Related Topics 树 深度优先搜索 二叉树 
// 👍 315 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.LinkedList;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
class SolutionOffer26 {


    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (B == null) {
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(A);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.val == B.val) {
                if (helper(node, B)) {
                    return true;
                }
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }

    /**
     * <p>
     * 如果node.val=B.val，判断B树中剩余节点与node节点值是否相同，如果不相同，A节点继续遍历直到与B根节点值相同重复调用helper方法
     * </p>
     *
     * @param nodeA A节点中与B树根节点值相等的节点
     * @param nodeB B节点
     * @return boolean
     * @author fuzy
     * @date 2021/8/8 17:57
     */
    private boolean helper(TreeNode nodeA, TreeNode nodeB) {
        Queue<TreeNode> queueA = new LinkedList<>();
        Queue<TreeNode> queueB = new LinkedList<>();
        queueA.offer(nodeA);
        queueB.offer(nodeB);

        while (!queueB.isEmpty()) {
            nodeA = queueA.poll();
            nodeB = queueB.poll();
            if (nodeA == null || nodeA.val != nodeB.val) {
                return false;
            }
            if (nodeB.left != null) {
                queueA.offer(nodeA.left);
                queueB.offer(nodeB.left);
            }
            if (nodeB.right != null) {
                queueA.offer(nodeA.right);
                queueB.offer(nodeB.right);
            }
        }

        return true;
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
