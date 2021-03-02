package com.github.black.hole.algorithm.leetcode;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/2/11
 */
public class Topic142 {

    /** 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。 */
    public static void main(String[] args) {
        int[] arr = IntStream.rangeClosed(0, 8).toArray();
        ListNode node = ListNode.buildCycleList(arr, 4);
        ListNode.printCycle(node);
        ListNode result = detectCycle2(node);
        System.out.println(result);
    }

    /**
     * 快慢指针, 到环入口为a 步， 环为 b 步
     *
     * <p>f = 2s, f = s + nb
     *
     * <p>s = nb, f = 2nb
     *
     * <p>指针走到环入口的步数 k = a + nb, s = nb, 所以slow 需要走a 步到环入口
     *
     * @param head
     * @return
     */
    public static ListNode detectCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode slow = head, fast = head;
        while (true) {
            slow = slow.next;
            fast = fast.next.next;
            if (Objects.equals(slow, fast)) {
                break;
            }
        }
        fast = head;
        while (!Objects.equals(slow, fast)) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }
}
