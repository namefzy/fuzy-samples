package com.fuzy.example.leetcode.editor.cn;
//请判断一个链表是否为回文链表。
//
// 示例 1: 
//
// 输入: 1->2
//输出: false 
//
// 示例 2: 
//
// 输入: 1->2->2->1
//输出: true
// 
//
// 进阶： 
//你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？ 
// Related Topics 链表 双指针 
// 👍 756 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution81 {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3= new ListNode(1);
        listNode2.next=listNode3;
        listNode1.next=listNode2;
        listNode.next=listNode1;
        isPalindrome1(listNode);
    }
    public static boolean isPalindrome2(ListNode head) {
        ListNode tail = getListNode(head);
        while(head!=null&&tail!=null){
            if(head.val!=tail.val){
                return false;
            }
            head = head.next;
            tail = tail.next;
        }
        return true;
    }
    public static ListNode getListNode(ListNode head){
        ListNode pre = null;
        ListNode cur = head;
        while(cur!=null){
            //记录下次遍历节点位置
            ListNode temp = cur.next;
            //第一次遍历指向null，第二次指向第一个节点
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    public static boolean isPalindrome1(ListNode head){
        if(head == null){
            return true;
        }
        ListNode firstHalfEnd = endOfFirstHalf(head);
        ListNode secondHalfStart = reverseList(firstHalfEnd);
        ListNode p1 = head;
        ListNode p2 = secondHalfStart;
        boolean result = true;
        //p1的长度始终大于p2 123456 12345
        while (result&&p2!=null){
            if(p1.val!=p2.val){
                result = false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        firstHalfEnd.next = reverseList(secondHalfStart);
        return result;
    }


    private static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr!=null){
            //下一次循环的节点 next
            ListNode nextTemp = curr.next;
            //第一个节点指向null,接下来一次指向当前节点
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return null;
    }

    /** 1-2-3-3-2-1
     * 123 321
     * 找到前半部分链表的尾节点并反转后半部分节点
     * @param head
     * @return
     */
    private  static ListNode endOfFirstHalf(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while (fast.next!=null&&fast.next.next!=null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public boolean isPalindrome(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        while (head!=null){
            list.add(head);
            head = head.next;
        }
        int left = 0;
        int right = list.size()-1;
        while (left<right){
            if(list.get(left).val!=list.get(right).val){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
