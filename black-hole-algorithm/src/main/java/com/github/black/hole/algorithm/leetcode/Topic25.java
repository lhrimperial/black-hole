package com.github.black.hole.algorithm.leetcode;

import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic25 {

    /**
     * 25. K 个一组翻转链表 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
     *
     * <p>k 是一个正整数，它的值小于或等于链表的长度。
     *
     * <p>如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     */
    public static void main(String[] args) {
        int[] arr = IntStream.rangeClosed(1,10).toArray();
        ListNode head = reverseKGroup(ListNode.buildList(arr), 3);
        ListNode.print(head);
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        ListNode a = head, b = head;
        for (int i = 0; i < k; i++) {
            if (b == null) {
                return head;
            }
            b = b.next;
        }
        ListNode newHead = reverse(a, b);
        a.next = reverseKGroup(b, k);
        return newHead;
    }

    public static ListNode reverse(ListNode a, ListNode b) {
        ListNode curr = a, pre = null, next = null;
        while (curr != b) {
            next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }
}
