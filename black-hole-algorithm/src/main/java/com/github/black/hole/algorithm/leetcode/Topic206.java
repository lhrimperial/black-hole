package com.github.black.hole.algorithm.leetcode;

import java.util.stream.IntStream;

import static com.github.black.hole.algorithm.leetcode.ListNode.buildList;
import static com.github.black.hole.algorithm.leetcode.ListNode.print;

/**
 * @author hairen.long
 * @date 2021/2/9
 */
public class Topic206 {

    /**
     * 反转链表
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = IntStream.rangeClosed(1, 6).toArray();
        ListNode ListNode = buildList(arr);
        print(reverseList2(ListNode));

    }

    /**
     * 使用双指针，把当前前驱作为后序，双指针往前移
     *
     * @param head
     * @return
     */
    public static ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode curr = head, prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    /**
     * 递归
     *
     * @param head
     * @return
     */
    public static ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 1 2 3 4 5 6 null
        ListNode reverseNode = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return reverseNode;
    }
}
