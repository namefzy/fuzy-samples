package com.fuzy.example.leetcode.editor.cn;
//反转一个单链表。
//
// 示例: 
//
// 输入: 1->2->3->4->5->NULL
//输出: 5->4->3->2->1->NULL 
//
// 进阶: 
//你可以迭代或递归地反转链表。你能否用两种方法解决这道题？ 
// Related Topics 链表 
// 👍 1304 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution75 {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1,new ListNode(2,new ListNode(3,new ListNode(4))));
        ListNode listNode1 = reverseList(listNode);
        while (listNode1!=null){

            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }
    /**
     * 循环解法
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {

        ListNode tmp = head;
        while(head!=null){
            if(head.next ==null){
                break;
            }
            head = head.next;
            head.next = tmp;

        }
        return head;
//       ListNode pre = null;
//       ListNode cur = head;
//       ListNode tmp;
//       while (cur!=null){
//           tmp = cur.next;
//           cur.next = pre;
//           pre = cur;
//           cur = tmp;
//       }
//       return pre;
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        ListNode(int x, ListNode listNode) {
            this.next = listNode;
            val = x;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
