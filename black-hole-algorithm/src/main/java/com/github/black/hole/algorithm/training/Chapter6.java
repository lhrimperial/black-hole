package com.github.black.hole.algorithm.training;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2021/8/11
 */
public class Chapter6 {

    /**
     * 206 反转链表
     *
     * <p>24 链表交互元素
     *
     * <p>141 链表是否有环
     */
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        //        ListNode result1 = reverseList1(ListNode.build(arr));
        //        ListNode.printf(result1);
        //        ListNode result2 = reverseList2(ListNode.build(arr));
        //        ListNode.printf(result2);
        //
        //        ListNode result3 = swapPairs1(ListNode.build(arr));
        //        ListNode.printf(result3);
        //
        //        ListNode result4 = swapPairs2(ListNode.build(arr));
        //        ListNode.printf(result4);

        ListNode node = ListNode.build(arr);
        System.out.println("has cycle " + hasCycle3(node));

        ListNode cycle = ListNode.buildCycle(arr, 3);
        System.out.println("has cycle " + hasCycle3(cycle));
    }

    public static boolean hasCycle3(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head.next, slow = head;
        while (fast != slow) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public static boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head, fast = head;
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasCycle1(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        HashSet<ListNode> nodeSet = new HashSet<>();
        while (head != null) {
            if (nodeSet.contains(head)) {
                return true;
            }
            nodeSet.add(head);
            head = head.next;
        }
        return false;
    }

    public static ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs2(next.next);
        next.next = head;
        return next;
    }

    public static ListNode swapPairs1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode preHead = new ListNode(-1);
        preHead.next = head;
        ListNode curr = preHead, next = null, twoNext = null;
        while (curr.next != null && curr.next.next != null) {
            next = curr.next;
            twoNext = curr.next.next;

            next.next = twoNext.next;
            twoNext.next = next;
            curr.next = twoNext;
            curr = next;
        }
        return preHead.next;
    }

    public static ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode node = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return node;
    }

    public static ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode curr = head, prev = null, next = null;
        while (curr != null) {
            next = curr.next;

            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
