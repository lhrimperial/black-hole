package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic234 {

    /** 234. 回文链表 */
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 4, 3, 2, 1};
        System.out.println(isPalindrome(ListNode.buildList(arr)));
    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast != null) {
            slow = slow.next;
        }
        ListNode right = reverse(slow);
        ListNode left = head;
        while (right != null) {
            if (right.value != left.value) {
                return false;
            }
            left = left.next;
            right = right.next;
        }
        return true;
    }

    private static ListNode reverse(ListNode node) {
        ListNode curr = node, pre = null, next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }
}
