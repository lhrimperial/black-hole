package com.github.black.hole.algorithm.leetcode;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/8/25
 */
public class AbsReview {

    public static void main(String[] args) throws Exception{

        String s = "abcadbcdbb";
        System.out.println(lengthOfLongestSubstring2(s));
        List<Integer> list = new LinkedList<>();
        list.add(1);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map = new ConcurrentHashMap<>();
        map.put(1, 1);
       Thread th =   new Thread(()->{

        });
       th.start();
       th.join();
        ExecutorService service = new ThreadPoolExecutor(
                3,
                5,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                new ThreadFactoryBuilder().setNameFormat("bio-server-pool").build(),
                new ThreadPoolExecutor.AbortPolicy());
        service.submit(()->{});


        //
        //        int[] arr = IntStream.rangeClosed(1, 10).toArray();
        //        ListNode node = reverseKGroup(ListNode.buildList(arr), 3);
        //        ListNode.print(node);
    }

    public static int lengthOfLongestSubstring2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int ans = 0, len = s.length();
        while (right < len) {
            char rc = s.charAt(right++);
            window.put(rc, window.getOrDefault(rc, 0) + 1);

            while (window.get(rc) > 1) {
                char lc = s.charAt(left++);
                window.put(lc, window.get(lc) - 1);
            }
            ans = Math.max(ans, right - left);
        }
        return ans;
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        ListNode a = head, b = head;
        for (int i = 0; i < k; i++) {
            if (b == null) {
                return head;
            }
            b = b.next;
        }
        ListNode newHead = reverse(a, b);
        a.next = reverseKGroup(b, k);
        return newHead;
    }

    public static ListNode reverse(ListNode head, ListNode tail) {
        ListNode curr = head, pre = null, next = null;
        while (curr != tail) {
            next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }
}
