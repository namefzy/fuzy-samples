package com.fuzy.example.leetcode.editor.cn;//给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
//
// 示例 1: 
//
// 输入: 1->2->3->4->5->NULL, k = 2
//输出: 4->5->1->2->3->NULL
//解释:
//向右旋转 1 步: 5->1->2->3->4->NULL
//向右旋转 2 步: 4->5->1->2->3->NULL
// 
//
// 示例 2: 
//
// 输入: 0->1->2->NULL, k = 4
//输出: 2->0->1->NULL
//解释:
//向右旋转 1 步: 2->0->1->NULL
//向右旋转 2 步: 1->2->0->NULL
//向右旋转 3 步: 0->1->2->NULL
//向右旋转 4 步: 2->0->1->NULL 
// Related Topics 链表 双指针 
// 👍 396 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class 旋转链表 {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1,new ListNode(2,new ListNode(3,new ListNode(4,new ListNode(5)))));
        rotateRight(listNode,2);
    }

    public static ListNode rotateRight(ListNode head, int k) {
        if(head==null||k==0){
            return head;
        }
        ListNode suffix = head;
        ListNode pre = head;
        ListNode temp = head;
        int length = 1;
        while (temp.next!=null){
            temp = temp.next;
            length++;
        }
        k = k%length;
        for (int i = 0; i < length-k; i++) {
            suffix = suffix.next;
            ListNode cacheNode = pre.next;
            pre.next = null;
            temp.next = pre;
            pre = cacheNode;
            temp = temp.next;
        }
        return suffix;
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }


    }
}
//leetcode submit region end(Prohibit modification and deletion)
