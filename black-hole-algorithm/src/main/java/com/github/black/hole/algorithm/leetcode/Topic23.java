package com.github.black.hole.algorithm.leetcode;

import java.util.PriorityQueue;

/**
 * @author hairen.long
 * @date 2021/9/17
 */
public class Topic23 {

    /**
     * 合并 k 个有序链表
     *
     * <p>时间复杂度是 O(Nlogk)
     */
    public static void main(String[] args) {
        int[] arr1 = {1, 3, 4, 5}, arr2 = {2, 4, 6}, arr3 = {5, 7, 8, 9};
        ListNode node =
                mergeKLists(
                        new ListNode[] {
                            ListNode.buildList(arr1),
                            ListNode.buildList(arr2),
                            ListNode.buildList(arr3)
                        });
        ListNode.print(node);
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        ListNode preHead = new ListNode(-1);
        ListNode curr = preHead;
        // 优先队列 小顶堆
        PriorityQueue<ListNode> queue =
                new PriorityQueue<>(lists.length, (a, b) -> a.value - b.value);
        for (ListNode head : lists) {
            if (head != null) {
                queue.add(head);
            }
        }
        while (!queue.isEmpty()) {
            ListNode node = queue.poll();
            curr.next = node;
            if (node.next != null) {
                queue.add(node.next);
            }
            curr = curr.next;
        }
        return preHead.next;
    }
}
