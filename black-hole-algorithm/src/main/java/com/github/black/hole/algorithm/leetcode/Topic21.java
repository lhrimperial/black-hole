package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/17
 */
public class Topic21 {

    /**
     * 合并两个有序链表
     * @param args
     */
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 4};
        int[] arr2 = {1, 3, 4};
        ListNode node = mergeTwoLists(ListNode.buildList(arr1), ListNode.buildList(arr2));
        ListNode.print(node);
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode preHead = new ListNode(-1);
        ListNode curr = preHead;
        while (l1 != null && l2 != null) {
            if (l1.value > l2.value) {
                curr.next = l2;
                l2 = l2.next;
            } else {
                curr.next = l1;
                l1 = l1.next;
            }
            curr = curr.next;
        }
        if (l1 != null) {
            curr.next = l1;
        }
        if (l2 != null) {
            curr.next = l2;
        }
        return preHead.next;
    }
}
