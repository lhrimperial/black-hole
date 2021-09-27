package com.github.black.hole.algorithm.leetcode;

import static com.github.black.hole.algorithm.leetcode.ListNode.buildList;
import static com.github.black.hole.algorithm.leetcode.ListNode.print;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic92 {

    /**
     * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
     */
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        print(reverseBetween1(buildList(arr), 2, 4));
    }

    public static ListNode reverseBetween1(ListNode head, int left, int right) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode preHead = new ListNode(-1);
        preHead.next = head;
        ListNode pre = preHead;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        ListNode curr = pre.next, next = null;
        for (int i = 0; i < right - left; i++) {
            next = curr.next;
            curr.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return preHead.next;
    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null) {
            return head;
        }
        if (left == 1) {
            return reverseN(head, right);
        }
        head.next = reverseBetween(head.next, left - 1, right - 1);
        return head;
    }

    private static ListNode after;

    private static ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            after = head.next;
            return head;
        }
        ListNode node = reverseN(head.next, n - 1);
        head.next.next = head;
        head.next = after;
        return node;
    }
}
