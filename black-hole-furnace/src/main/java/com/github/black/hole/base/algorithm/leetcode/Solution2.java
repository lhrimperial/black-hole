package com.github.black.hole.base.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2020/10/27
 */
public class Solution2 {

    /**
     * 给出两个非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，并且它们的每个节点只能存储一位数字。
     * <p>
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * <p>
     * 您可以假设除了数字 0 之外，这两个数都不会以 0开头。
     *
     * @param args
     */

    public static void main(String[] args) {
//        987 + 23
        ListNode l1 = buildListNode(new int[]{9, 8, 7});
        ListNode l2 = buildListNode(new int[]{2, 3});
        ListNode resp = addTwoNumbers(l1, l2);
        while (resp != null) {
            System.out.print(resp.val + "\t");
            resp = resp.next;
        }
    }

    public static ListNode buildListNode(int[] arr) {
        if (arr == null) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode node = head;
        for (int i = 1, len = arr.length; i < len; i++) {
            node.next = new ListNode(arr[i]);
            node = node.next;
        }
        return head;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode cur = head;

        int carry = 0;
        while (l1 != null || l2 != null) {
            int x = l1 != null ? l1.val : 0;
            int y = l2 != null ? l2.val : 0;

            int sum = x + y + carry;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;

            carry = sum / 10;

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            cur.next = new ListNode(carry);
        }
        return head.next;
    }


    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
