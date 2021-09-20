package com.github.black.hole.algorithm.leetcode;

import java.util.HashSet;

/**
 * @author hairen.long
 * @date 2021/2/10
 */
public class ListNode {
    int value;
    ListNode next;

    public ListNode(int value) {
        this.value = value;
    }

    public static ListNode buildList(int[] arr) {
        if (arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode node = head;
        for (int i = 1, len = arr.length; i < len; i++) {
            node.next = new ListNode(arr[i]);
            node = node.next;
        }
        return head;
    }

    public static ListNode buildCycleList(int[] arr, int index) {
        if (arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode node = head;
        ListNode cycleNode = null, tail = null;
        for (int i = 1, len = arr.length; i < len; i++) {
            node.next = new ListNode(arr[i]);
            if (i == index) {
                cycleNode = node.next;
            }
            node = node.next;
            tail = node;
        }
        tail.next = cycleNode;
        return head;
    }

    public static void print(ListNode node) {
        if (node == null) {
            return;
        }
        while (node != null) {
            System.out.print(node.value + "\t");
            node = node.next;
        }
        System.out.println();
    }

    public static void printCycle(ListNode node) {
        if (node == null) {
            return;
        }
        HashSet<ListNode> nodeSet = new HashSet<>();
        while (node != null) {
            System.out.print(node.value + "\t");
            if (nodeSet.contains(node)) {
                System.out.println();
                return;
            }
            nodeSet.add(node);
            node = node.next;
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "" + this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ListNode) {
            return this.value == ((ListNode) obj).value;
        }
        return false;
    }
}
