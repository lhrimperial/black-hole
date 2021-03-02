package com.github.black.hole.algorithm.leetcode;

import java.util.HashSet;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/2/11
 */
public class Topic141 {

    /** 给定一个链表，判断链表中是否有环。 */
    public static void main(String[] args) {
        int[] arr1 = IntStream.rangeClosed(0, 6).toArray();
        ListNode node1 = ListNode.buildList(arr1);
        ListNode.print(node1);
        System.out.println("node is cycle " + hasCycle3(node1));
        int[] arr2 = new int[] {1, 2, 3, 4, 5, 6, 7};
        ListNode node2 = ListNode.buildCycleList(arr2, 3);
        ListNode.printCycle(node2);
        System.out.println("node is cycle " + hasCycle3(node2));
    }

    public static boolean hasCycle3(ListNode head){
        if (head == null || head.next == null){
            return false;
        }
        ListNode slow = head, fast = head.next;
        while (!Objects.equals(slow,fast)){
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public static boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            System.out.println("slow=" + slow.value + ",fast=" + fast.value);
            if (Objects.equals(fast, slow)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过Set判重
     *
     * @param head
     * @return
     */
    public static boolean hasCycle1(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        HashSet<ListNode> nodeSet = new HashSet<>();
        while (head != null) {
            if (nodeSet.contains(head)) {
                return true;
            }
            nodeSet.add(head);
            head = head.next;
        }
        return false;
    }
}
