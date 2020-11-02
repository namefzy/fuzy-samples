package com.fuzy.example.leetcode.editor.cn;
//给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
//
// 示例： 
//
// 给定一个链表: 1->2->3->4->5, 和 n = 2.
//
//当删除了倒数第二个节点后，链表变为 1->2->3->5.
// 
//
// 说明： 
//
// 给定的 n 保证是有效的。 
//
// 进阶： 
//
// 你能尝试使用一趟扫描实现吗？ 
// Related Topics 链表 双指针 
// 👍 1093 👎 0


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
class Solution76 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //构造哑节点，它的next指针指向链表头节点，这样就不需要对头节点进行特殊的判断了
        ListNode dummy = new ListNode(0,head);

        int length = 0;
        ListNode cur = dummy;
        while (head!=null){
            head = head.next;
            length++;
        }
        //length产生的结果会比原来大1，所以在减去n需要+1
        for (int i = 1; i < length - n + 1; i++) {
            cur = cur.next;
        }
        cur.next = cur.next.next;
        ListNode next = dummy.next;

        return next;
    }

    public ListNode removeNthFromEnd1(ListNode head,int n){
        ListNode dummy = new ListNode(0,head);
        ListNode p = dummy;
        ListNode q = dummy;
        while(n!=0){
            p = p.next;
            n--;
        }
        while(p.next!=null){
            p = p.next;
            q = q.next;
        }
        q.next = q.next.next;
        return dummy.next;
    }

    public class ListNode {
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
