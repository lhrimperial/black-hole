package com.github.black.hole.base.algorithm.leetcode;

import java.util.Objects;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2020/10/16
 */
public class ReverseListSolution {

    /**
     * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
     *
     * @param args
     */
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

        // System.out.println("\n翻转1：");
        // print(reverseList1(head));
    }

    /**
     * 栈方式
     *
     * @param head
     * @return
     */
    public static ListNode reverseList1(ListNode head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }
        Stack<ListNode> stack = new Stack<>();
        while (Objects.nonNull(head.next)) {
            stack.push(head);
            head = head.next;
        }

        ListNode node = head;
        while (!stack.isEmpty()) {
            node.next = stack.pop();
            node = node.next;
        }
        node.next = null;
        return head;
    }

    /**
     * 快慢指针
     *
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }

        ListNode cur = head;
        ListNode pre = null;
        while (Objects.nonNull(cur)) {
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
