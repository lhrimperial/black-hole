package com.github.black.hole.algorithm.leetcode;

import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/2/10
 */
public class Topic24 {

    /** 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。 */
    public static void main(String[] args) {
        int[] arr = IntStream.rangeClosed(0, 6).toArray();
        ListNode node = ListNode.buildList(arr);
        ListNode.print(swapPairs2(node));
    }

    public static ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        ListNode curr = preHead, next = null, twoNext = null;
        while (curr.next != null && curr.next.next != null) {
            next = curr.next;
            twoNext = curr.next.next;

            curr.next = twoNext;
            next.next = twoNext.next;
            twoNext.next = next;
            curr = next;
        }
        return preHead.next;
    }

    /**
     * 递归
     *
     * @param head
     * @return
     */
    public static ListNode swapPairs1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs1(next.next);
        next.next = head;
        return next;
    }
}
