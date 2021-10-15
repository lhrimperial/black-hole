package com.github.black.hole.algorithm.leetcode;

import java.util.List;

/**
 * @author hairen.long
 * @date 2021/10/11
 */
public class Topic61 {

    /**
     * 61. 旋转链表
     *
     */
    public static void main(String[] args) {

    }

    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }
        int len = 1;
        ListNode point = head;
        while (point.next != null) {
            len++;
            point = point.next;
        }
        point.next = head;

        k = k % len;

        int count = 0;
        ListNode pre = head;
        while (count <= len - k - 1) {
            count++;
            pre = pre.next;
        }
        ListNode ans = pre.next;
        pre.next = null;
        return ans;
    }
}
