package com.github.black.hole.algorithm.training;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/8/21
 */
public class DayReview {

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};
        int result = maxProfit(arr);
        System.out.println(result);
    }

    public static int maxProfit(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int result = 0, prev = arr[0];
        for (int i = 1, len = arr.length; i < len; i++) {
            if (prev < arr[i]) {
                result += arr[i] - prev;
            }
            prev = arr[i];
        }
        return result;
    }

    public static void test() {
        int[] arr = IntStream.range(1, 7).toArray();
        System.out.println("twoSum1:" + Arrays.toString(twoSum1(arr, 9)));
        System.out.println("twoSum2:" + Arrays.toString(twoSum2(arr, 9)));

        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println(threeSum1(nums).toString());
        System.out.println(threeSum2(nums).toString());
        System.out.println(threeSum3(nums).toString());

        int[] arr1 = {2, 4, 3}, arr2 = {5, 6, 4};
        ListNode l1 = ListNode.build(arr1);
        ListNode l2 = ListNode.build(arr2);
        ListNode result = addTwoNumbers(l1, l2);
        ListNode.printf(result);

        ListNode result1 = reverseList1(ListNode.build(arr));
        ListNode.printf(result1);
        ListNode.printf(reverseList2(ListNode.build(arr)));

        ListNode.printf(swapPairs1(ListNode.build(arr)));
        ListNode.printf(swapPairs2(ListNode.build(arr)));

        System.out.println("has cycle : " + hasCycle1(ListNode.buildCycle(arr, 3)));
        System.out.println("has cycle : " + hasCycle2(ListNode.buildCycle(arr, 3)));
        System.out.println("has cycle : " + hasCycle3(ListNode.buildCycle(arr, 3)));

        System.out.println("isValid : " + isValid1("({[]}{})"));
        System.out.println("isValid : " + isValid2("({[]}{})"));

        int[] arr11 = {1, 3, -1, -3, -4, 5, 3, 6, 7};
        System.out.println(Arrays.toString(maxSlidingWindow1(arr11, 3)));
        System.out.println(Arrays.toString(maxSlidingWindow2(arr11, 3)));
        System.out.println(Arrays.toString(maxSlidingWindow3(arr11, 3)));
    }

    public static int[] maxSlidingWindow3(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] ans = new int[arr.length - k + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0, len = arr.length; i < len; i++) {
            while (!queue.isEmpty() && arr[queue.peekLast()] <= arr[i]) {
                queue.pollLast();
            }
            queue.offer(i);
            if (queue.peek() <= i - k) {
                queue.poll();
            }
            if (i >= k - 1) {
                ans[i - k + 1] = arr[queue.peek()];
            }
        }
        return ans;
    }

    public static int[] maxSlidingWindow2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] ans = new int[arr.length - k + 1];
        Queue<Integer> maxQueue = new PriorityQueue<>(3, (v1, v2) -> v2 - v1);
        for (int i = 0, len = arr.length; i < len; i++) {
            if (maxQueue.size() >= k) {
                maxQueue.remove(arr[i - k]);
            }
            maxQueue.offer(arr[i]);
            if (i >= k - 1) {
                ans[i - k + 1] = maxQueue.peek();
            }
        }
        return ans;
    }

    public static int[] maxSlidingWindow1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] ans = new int[arr.length - k + 1];
        for (int i = 0, len = ans.length; i < len; i++) {
            int max = arr[i];
            for (int j = i + 1; j < i + k; j++) {
                max = Math.max(max, arr[j]);
            }
            ans[i] = max;
        }
        return ans;
    }

    public static boolean isValid2(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        Deque<Character> stack = new ArrayDeque<>();
        for (Character ch : s.toCharArray()) {
            if (map.containsKey(ch)) {
                if (stack.isEmpty() || !Objects.equals(map.get(ch), stack.peek())) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }

    public static boolean isValid1(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        Deque<Character> stack = new ArrayDeque<>();
        for (Character ch : s.toCharArray()) {
            if (map.containsKey(ch)) {
                stack.push(ch);
            } else if (stack.isEmpty() || !Objects.equals(map.get(stack.pop()), ch)) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    public static boolean hasCycle3(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head, fast = head.next;
        while (!Objects.equals(slow, fast)) {
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
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasCycle1(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        Set<ListNode> existSet = new HashSet<>();
        while (head != null) {
            if (existSet.contains(head)) {
                return true;
            }
            existSet.add(head);
            head = head.next;
        }
        return false;
    }

    public static ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs2(next.next);
        next.next = head;
        return next;
    }

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

            next.next = twoNext.next;
            twoNext.next = next;
            curr.next = twoNext;
            curr = next;
        }
        return preHead.next;
    }

    public static ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode node = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return node;
    }

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

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode head = new ListNode(-1);
        ListNode node = head;
        int x = 0, y = 0, s = 0, carry = 0;
        while (l1 != null || l2 != null) {
            x = l1 == null ? 0 : l1.val;
            y = l2 == null ? 0 : l2.val;
            s = x + y + carry;
            node.next = new ListNode(s % 10);
            node = node.next;

            carry = s / 10;

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            node.next = new ListNode(carry);
        }
        return head.next;
    }

    public static List<List<Integer>> threeSum3(int[] arr) {
        if (arr == null || arr.length < 3) {
            return Collections.emptyList();
        }
        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0, len = arr.length; i < len; i++) {
            if (i > 0 && arr[i] == arr[i - 1]) {
                continue;
            }
            int l = i + 1, r = len - 1, s = 0;
            while (l < r) {
                s = arr[i] + arr[l] + arr[r];
                if (s > 0) {
                    r--;
                } else if (s < 0) {
                    l++;
                } else {
                    result.add(Arrays.asList(arr[i], arr[l], arr[r]));
                    while (l < r && arr[l] == arr[l + 1]) {
                        l++;
                    }
                    while (l < r && arr[r] == arr[r - 1]) {
                        r--;
                    }
                    l++;
                    r--;
                }
            }
        }
        return result;
    }

    public static List<List<Integer>> threeSum2(int[] arr) {
        if (arr == null || arr.length < 3) {
            return Collections.emptyList();
        }
        Arrays.sort(arr);
        Set<List<Integer>> result = new HashSet<>();
        Set<Integer> existSet = null;
        for (int i = 0, len = arr.length; i < len; i++) {
            if (i > 0 && arr[i] == arr[i - 1]) {
                continue;
            }
            existSet = new HashSet<>();
            for (int j = i + 1; j < len; j++) {
                if (j > i + 1 && arr[j] == arr[j - 1]) {
                    continue;
                }
                int value = -(arr[i] + arr[j]);
                if (existSet.contains(arr[j])) {
                    result.add(Arrays.asList(arr[i], value, arr[j]));
                }
                existSet.add(value);
            }
        }
        return new ArrayList<>(result);
    }

    public static List<List<Integer>> threeSum1(int[] arr) {
        if (arr == null || arr.length < 3) {
            return Collections.emptyList();
        }
        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0, len = arr.length; i < len; i++) {
            if (i > 0 && arr[i] == arr[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < len; j++) {
                if (j > i + 1 && arr[j] == arr[j - 1]) {
                    continue;
                }
                for (int n = j + 1; n < len; n++) {
                    if (n > j + i && arr[n] == arr[n - 1]) {
                        continue;
                    }
                    if (arr[i] + arr[j] + arr[n] == 0) {
                        result.add(Arrays.asList(arr[i], arr[j], arr[n]));
                    }
                }
            }
        }
        return result;
    }

    public static int[] twoSum2(int[] arr, int target) {
        if (arr == null || arr.length < 2) {
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

    public static int[] twoSum1(int[] arr, int target) {
        if (arr == null || arr.length < 2) {
            return new int[0];
        }
        for (int i = 0, len = arr.length; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[0];
    }
}
