package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/8/25
 */
public class AbsReview {

    public static void main(String[] args) {
        int a = 1;
        Map<String,String> map = new HashMap<>(9);
        map.put("1", "2");
        Map<String,String> conMap = new ConcurrentHashMap<>();
        conMap.put("1", "2");

        int[] num1 = {1, 3, 4, 9};
        int[] num2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(findMedianSortedArrays(num1, num2));
    }


    public static double findMedianSortedArrays1(int[] arr1, int[] arr2) {
        return 0d;
    }

    public static double findMedianSortedArrays(int[] arr1, int[] arr2) {
        assert arr1 != null;
        assert arr2 != null;
        int n = arr1.length, m = arr2.length;
        int left = -1, right = -1, aStart = 0, bStart = 0;
        for (int i = 0, k = (n + m) >> 1; i <= k; i++) {
            left = right;
            if (aStart < n && (bStart >= m || arr1[aStart] < arr2[bStart])) {
                right = arr1[aStart++];
            } else {
                right = arr2[bStart++];
            }
        }
        if (((n + m) & 1) == 1) {
            return right;
        } else {
            return (left + right) / 2d;
        }
    }

    public static int lengthOfLongestSubstring1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Set<Character> set = new HashSet<>();
        int rk = -1, ans = 0;
        for (int i = 0, len = s.length(); i < len; i++) {
            if (i > 0) {
                set.remove(s.charAt(i));
            }
            while (rk + 1 < len && !set.contains(s.charAt(rk + 1))) {
                set.add(s.charAt(rk + 1));
                rk++;
            }
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, ans = 0;
        for (int i = 0, len = s.length(); i < len; i++) {
            char ch = s.charAt(i);
            if (map.containsKey(ch)) {
                left = Math.max(left, map.get(ch) + 1);
            }
            map.put(ch, i);
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }

    public static ListNode addTwoNum(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode preHead = new ListNode(-1);
        ListNode curr = preHead;
        int x = 0, y = 0, s = 0, carry = 0;
        while (l1 != null || l2 != null) {
            x = l1 == null ? 0 : l1.value;
            y = l2 == null ? 0 : l2.value;
            s = x + y + carry;

            curr.next = new ListNode(s % 10);
            curr = curr.next;

            carry = s / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return preHead.next;
    }

    public static int[] twoSum(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0, len = arr.length; i < len; i++) {
            int value = target - arr[i];
            if (map.containsKey(value)) {
                return new int[] {map.get(value), i};
            }
            map.put(arr[i], i);
        }
        return new int[0];
    }
}
