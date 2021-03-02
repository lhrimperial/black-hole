package com.github.black.hole.algorithm.leetcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/2/20
 */
public class TopicListTest {
    public static void main(String[] args) {
        int[] array = IntStream.rangeClosed(1, 9).toArray();
        print("原链表", build(array));

        print("两两交换节点", swapPairs(build(array)));
        print("两两交换节点1", swapPairs1(build(array)));

        print("反转链表", reverseList(build(array)));
        print("反转链表1", reverseList1(build(array)));

        ListNode cycleNode = buildCycle(array, 4);
        printCycle("循环链表", cycleNode);
        System.out.println("是否是循环链表：" + isCycle(cycleNode));
        System.out.println("是否是循环链表1：" + isCycle1(cycleNode));
        System.out.println("是否是循环链表2：" + isCycle2(cycleNode));

        System.out.println("循环链表入口：" + detectCycle(cycleNode));
    }

    /**
     * 循环链表入口
     *
     * @param head
     * @return
     */
    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode slow = head, fast = head;
        while (true) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                break;
            }
        }
        fast = head;
        while (fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 循环链表
     *
     * @param head
     * @return
     */
    public static boolean isCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head, fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public static boolean isCycle1(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            if (slow == fast) {
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

    public static boolean isCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (!set.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 反转链表
     *
     * @param head
     * @return
     */
    public static ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode curr = head, prev = null, next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode node = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return node;
    }

    /**
     * 两两交换链表节点
     *
     * @param head
     * @return
     */
    public static ListNode swapPairs1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode preHead = new ListNode(-1);
        preHead.next = head;
        ListNode curr = preHead, next = null, twoNext = null;
        while (curr.next != null && curr.next.next != null) {
            next = curr.next;
            twoNext = curr.next.next;

            curr.next = twoNext;
            next.next = twoNext.next;
            twoNext.next = next;
            curr = next;
        }
        return preHead.next;
    }

    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }

    private static void printCycle(String title, ListNode head) {
        if (head == null) {
            return;
        }
        System.out.println(title);
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            System.out.print(head.value + "\t");
            if (!set.add(head)) {
                break;
            }
            head = head.next;
        }
        System.out.println();
    }

    private static ListNode buildCycle(int[] array, int cycle) {
        if (array == null) {
            return null;
        }
        ListNode head = new ListNode(array[0]);
        ListNode node = head, cycleNode = null;
        for (int i = 1, len = array.length; i < len; i++) {
            node.next = new ListNode(array[i]);
            node = node.next;
            if (i == cycle) {
                cycleNode = node;
            }
        }
        node.next = cycleNode;
        return head;
    }

    private static void print(String title, ListNode head) {
        if (head == null) {
            return;
        }
        System.out.println(title);
        while (head != null) {
            System.out.print(head.value + "\t");
            head = head.next;
        }
        System.out.println();
    }

    private static ListNode build(int[] array) {
        if (array == null) {
            return null;
        }
        ListNode head = new ListNode(array[0]);
        ListNode node = head;
        for (int i = 1, len = array.length; i < len; i++) {
            node.next = new ListNode(array[i]);
            node = node.next;
        }
        return head;
    }

    private static class ListNode {
        private int value;
        private ListNode next;

        public ListNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + "value=" + value + '}';
        }
    }
}
