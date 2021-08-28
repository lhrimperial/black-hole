package com.github.black.hole.algorithm.training;

import com.google.common.base.Objects;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author hairen.long
 * @date 2021/8/20
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public static ListNode buildCycle(int[] arr, int index) {
        if (arr == null || arr.length < 1) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode node = head, cycleNode = null;
        for (int i = 1, len = arr.length; i < len; i++) {
            node.next = new ListNode(arr[i]);
            if (i == index) {
                cycleNode = node.next;
            }
            node = node.next;
        }
        node.next = cycleNode;
        return head;
    }

    public static void printfCycle(ListNode head) {
        if (head == null) {
            return;
        }
        HashSet<ListNode> nodeSet = new HashSet<>();
        while (head != null) {
            System.out.print(head.val + "\t");
            if (nodeSet.contains(head)) {
                System.out.println();
                return;
            }
            nodeSet.add(head);
            head = head.next;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ListNode) {
            return val == ((ListNode) obj).val;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(val);
    }

    public static ListNode build(int[] arr) {
        if (arr == null || arr.length < 1) {
            return null;
        }
        System.out.println("build list node" + Arrays.toString(arr));
        ListNode head = new ListNode(arr[0]);
        ListNode curr = head;
        for (int i = 1, len = arr.length; i < len; i++) {
            curr.next = new ListNode(arr[i]);
            curr = curr.next;
        }
        return head;
    }

    public static void printf(ListNode head) {
        if (head == null) {
            return;
        }
        while (head != null) {
            System.out.print(head.val + "\t");
            head = head.next;
        }
        System.out.println();
    }
}
