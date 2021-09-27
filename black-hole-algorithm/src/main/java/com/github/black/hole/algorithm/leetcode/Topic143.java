package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/9/24
 */
public class Topic143 {
    /** 143. 重排链表 */
    public static void main(String[] args) {
        int[] arr1 = {1, 3, 5, 7, 9, 2, 4, 6, 8};
        ListNode node = ListNode.buildList(arr1);
        reorderList1(node);
        ListNode.print(node);
    }

    public static void reorderList1(ListNode head) {
        if (head == null) {
            return;
        }
        List<ListNode> list = new ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            list.add(curr);
            curr = curr.next;
        }
        int i = 0, j = list.size() - 1;
        while (i < j) {
            list.get(i++).next = list.get(j);
            if (i == j) {
                break;
            }
            list.get(j--).next = list.get(i);
        }
        list.get(i).next = null;
    }

    public static void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode mid = mid(head);
        ListNode l2 = reverse(mid.next);
        mid.next = null;
        merge(head, l2);
    }

    private static void merge(ListNode l1, ListNode l2) {
        ListNode t1, t2;
        while (l1 != null && l2 != null) {
            t1 = l1.next;
            l1.next = l2;
            l1 = t1;

            t2 = l2.next;
            l2.next = l1;
            l2 = t2;
        }
    }

    private static ListNode reverse(ListNode head) {
        ListNode curr = head, prev = null, next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    private static ListNode mid(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
