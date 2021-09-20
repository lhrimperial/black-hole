package com.github.black.hole.algorithm.leetcode;

import java.util.Objects;

/**
 * @author hairen.long
 * @date 2021/9/17
 */
public class Topic160 {

    /**
     * 两个链表是否相交
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] arr1 = {4, 1, 8, 4, 5}, arr2 = {5, 0, 1, 8, 4, 5};
        ListNode node = getIntersectionNode(ListNode.buildList(arr1), ListNode.buildList(arr2));
        System.out.println(node.value);
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        while (!Objects.equals(pA, pB)) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }
}
