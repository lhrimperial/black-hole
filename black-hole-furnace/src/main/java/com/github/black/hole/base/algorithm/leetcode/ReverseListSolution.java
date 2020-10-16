package com.github.black.hole.base.algorithm.leetcode;

import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2020/10/16
 */
public class ReverseListSolution {

    public static void main(String[] args) {
        int[] array = IntStream.rangeClosed(1, 10).toArray();
        ListNode head = new ListNode(array[0]);
        ListNode node = head;
        for (int i = 1; i < array.length; i++) {
            node.next = new ListNode(array[i]);
            node = node.next;
        }
        print(head);
        System.out.println("翻转：");
        print(reverseList(head));
    }

    public static ListNode reverseList(ListNode head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }
        ListNode cur = head;

        ListNode pre = null;
        while (cur != null) {
            ListNode node = cur.next;
            cur.next = pre;

            pre = cur;
            cur = node;
        }
        return pre;
    }

    private static void print(ListNode head) {
        if (Objects.isNull(head)) {
            return;
        }
        System.out.print(head.value + "\t");
        print(head.next);
    }

    private static class ListNode {
        private int value;
        private ListNode next;

        public ListNode(int value) {
            this.value = value;
        }
    }
}
