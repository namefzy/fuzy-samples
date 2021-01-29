package com.fuzy.example.leetcode.editor.cn;//给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
//
// 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。 
//
// 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。 
//
// 示例： 
//
// 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
//输出：7 -> 0 -> 8
//原因：342 + 465 = 807
// 
// Related Topics 链表 数学 
// 👍 5117 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution67 {

    public ListNode addTwoNumbers2(ListNode l1,ListNode l2){
        ListNode node = new ListNode(-1);
        ListNode pre = node;
        int flag = 0;
        while (l1!=null||l2!=null){
            int a = l1==null?0:l1.val;
            int b = l2==null?0:l2.val;
            int sum = a+b+flag;
            node.next = new ListNode(sum%10);
            if(sum>=10){
                flag = 1;
            }else{
                flag = 0;
            }
            if(l1!=null){
                l1 = l1.next;
            }
            if(l2!=null){
                l2 = l2.next;
            }
            node = node.next;
        }

        if(flag==1){
            node.next = new ListNode(1);
        }
        return pre.next;
    }

    //题目看错了的解法
    public static ListNode addTwoNumbers1(ListNode l1, ListNode l2){
        ListNode p1 = reverse(l1);
        ListNode p2 = reverse(l2);
        ListNode sentinel = new ListNode();
        int flag = 0;
        while (p1!=null||p2!=null){
            if(p1==null){
                if(p2.val+flag>=10){
                    sentinel.next = new ListNode((p2.val+flag)%10);
                    flag = 1;
                }else{
                    sentinel.next = new ListNode(p2.val+flag);
                    flag = 0;
                }

                p2=p2.next;
            }else if(p2==null){
                if(p1.val+flag>=10){
                    sentinel.next = new ListNode((p1.val+flag)%10);
                    flag = 1;
                }else{
                    sentinel.next = new ListNode(p1.val+flag);
                    flag = 0;
                }
                p1=p1.next;
            }else{
                if(p1.val+p2.val+flag>=10){
                    sentinel.next = new ListNode((p1.val+p2.val+flag)%10);
                    flag = 1;
                }else{
                    sentinel.next = new ListNode(p1.val+p2.val+flag);
                    flag = 0;
                }
                p1=p1.next;
                p2=p2.next;
            }

            sentinel = sentinel.next;

        }
        if(flag == 1){
            sentinel.next = new ListNode(1);
        }
        return sentinel.next;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(2);
        ListNode listNode1 = new ListNode(4);
        ListNode listNode2 = new ListNode(3);
        listNode.next = listNode1;
        listNode1.next = listNode2;

        ListNode listNode3 = new ListNode(5);
        ListNode listNode31 = new ListNode(6);
        ListNode listNode32 = new ListNode(4);
        listNode3.next = listNode31;
        listNode31.next = listNode32;

        addTwoNumbers1(listNode,listNode3);
    }

    private static ListNode reverse(ListNode node) {
        ListNode pre = null;
        ListNode cur = node;
        while (cur!=null){
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null,tail = null;
        int carry = 0;
        while (l1!=null||l2!=null){
            int x = l1!=null?l1.val:0;
            int y = l2!=null?l2.val:0;
            int sum = x+y+carry;
            carry = sum/10;
            sum = sum%10;
            if(head ==null ){
                head = tail = new ListNode(sum);
            }else{
                tail.next = new ListNode(sum);
            }
            if(l1!=null){
                l1 = l1.next;
            }
            if(l2!=null){
                l2 = l2.next;
            }
        }
        if(carry == 1){
            tail.next = new ListNode(carry);
        }
        return head;
    }
public static class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
}
//leetcode submit region end(Prohibit modification and deletion)
