package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/2/22
 */
public class Topic2 {

    /**
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     *
     * <p>请你将两个数相加，并以相同形式返回一个表示和的链表。
     *
     * <p>你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     *
     * <p>输入：l1 = [2,4,3], l2 = [5,6,4] 输出：[7,0,8] 解释：342 + 465 = 807.
     */
    public static void main(String[] args) {
        int[] arr1 = {9, 9, 9, 9, 9, 9, 9}, arr2 = {9, 9, 9, 9};
        ListNode l1 = ListNode.buildList(arr1);
        ListNode l2 = ListNode.buildList(arr2);
        ListNode result = addTwoNumbers(l1, l2);
        ListNode.print(result);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode preHead = new ListNode(-1);
        ListNode sumNode = preHead;
        int x = 0, y = 0, sum = 0, carry = 0;
        while (l1 != null || l2 != null) {
            x = l1 == null ? 0 : l1.value;
            y = l2 == null ? 0 : l2.value;
            sum = x + y + carry;
            sumNode.next = new ListNode(sum % 10);
            sumNode = sumNode.next;

            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            sumNode.next = new ListNode(carry);
        }
        return preHead.next;
    }
}
