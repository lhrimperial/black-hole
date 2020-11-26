package com.github.black.hole.base.algorithm.practice.chapter7;

import org.junit.Test;

import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2020/11/11
 */
public class LinkedListAlgo {

    /**
     * 1) 单链表反转
     *
     * <p>2) 链表中环的检测
     *
     * <p>3) 两个有序的链表合并
     *
     * <p>4) 删除链表倒数第n个结点
     *
     * <p>5) 求链表的中间结点
     */
    @Test
    public void testInverse() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Node head = build(arr);
        printList(head);
        Node inverseList = inverseLinkedList(head);
        printList(inverseList);
    }

    @Test
    public void mergeListTest() {
        Node list1 = build(new int[] {1, 3, 5, 7, 9});
        Node list2 = build(new int[] {2, 4, 6, 8, 10, 11, 12, 15});
        Node result = mergeLinkedList(list1, list2);
        printList(result);
    }

    @Test
    public void deleteKthTest() {
        Node list = build(IntStream.rangeClosed(1, 20).toArray());
        printList(list);
        Node after = deleteLastKth(list, 5);
        printList(after);
    }

    @Test
    public void findMidTest() {
        Node list = build(IntStream.rangeClosed(1, 20).toArray());
        printList(list);
        Node mid = findMidNode(list);
        System.out.println(mid.data);
    }

    public static Node findMidNode(Node list) {
        if (list == null) {
            return null;
        }
        Node fast = list;
        Node slow = list;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public static Node deleteLastKth(Node list, int k) {
        Node fast = list;
        int i = 1;
        while (fast != null && i < k) {
            fast = fast.next;
            i++;
        }
        if (fast == null) {
            return list;
        }
        Node slow = list;
        Node pre = null;
        while (fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next;
        }
        if (pre == null) {
            list = list.next;
        } else {
            pre.next = pre.next.next;
        }
        return list;
    }

    public static Node mergeLinkedList(Node list1, Node list2) {
        Node solider = new Node(-1);
        Node p = solider;
        while (list1 != null && list2 != null) {
            if (list1.data < list2.data) {
                p.next = list1;
                list1 = list1.next;
            } else {
                p.next = list2;
                list2 = list2.next;
            }
            p = p.next;
        }
        if (list1 != null) {
            p.next = list1;
        }
        if (list2 != null) {
            p.next = list2;
        }
        return solider.next;
    }

    /**
     * 环检测
     *
     * @param list
     * @return
     */
    public static boolean checkCircle(Node list) {
        if (list == null) {
            return false;
        }
        Node fast = list.next;
        Node slow = list;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    public static Node inverseLinkedList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node pre = null;
        Node curr = head;
        Node next = null;
        while (curr != null) {
            next = curr.next;

            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    private static void printList(Node head) {
        while (head != null) {
            System.out.print(head.data + "\t");
            head = head.next;
        }
        System.out.println();
    }

    private static Node build(int[] arr) {
        if (arr.length > 0) {
            Node head = new Node(arr[0]);
            Node node = head;
            for (int i = 1, len = arr.length; i < len; i++) {
                node.next = new Node(arr[i]);
                node = node.next;
            }
            return head;
        }
        return null;
    }

    private static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }
    }
}
