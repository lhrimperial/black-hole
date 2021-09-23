package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/17
 */
public class Topic19 {

    /** 删除链表的倒数第 N 个结点 */
    public static void main(String[] args) {
        int[] arr = {1, 2, 3,4,5,6,7};
        ListNode node = removeNthFromEnd1(ListNode.buildList(arr), 3);
        System.out.println(node == null ? "null" : node.value);
        ListNode.print(node);
    }

    public static ListNode removeNthFromEnd1(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode preHead = new ListNode(-1);
        preHead.next = head;
        ListNode fast = head;
        ListNode slow = preHead;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return preHead.next;
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode preHead = new ListNode(-1);
        preHead.next = head;
        ListNode node = findFromEnd(preHead, n + 1);
        node.next = node.next.next;
        return preHead.next;
    }

    // 返回链表的倒数第 k 个节点
    public static ListNode findFromEnd(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        ListNode l1 = head;
        for (int i = 0; i < k; i++) {
            l1 = l1.next;
        }
        ListNode l2 = head;
        while (l1 != null) {
            l1 = l1.next;
            l2 = l2.next;
        }
        return l2;
    }
}
