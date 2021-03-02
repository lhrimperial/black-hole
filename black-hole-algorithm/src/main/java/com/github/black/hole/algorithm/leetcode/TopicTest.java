package com.github.black.hole.algorithm.leetcode;

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
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/2/19
 */
public class TopicTest {

    public static void main(String[] args) {
        KthLargest kthLargest = new KthLargest(3, new int[] {4, 5, 8, 2, 10});
        System.out.println("第k大：" + kthLargest.kthLargest());
    }

    private static class KthLargest {
        private int k;
        private Queue<Integer> queue;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.queue = new PriorityQueue<>(k);
            if (nums != null) {
                for (int i : nums) {
                    add(i);
                }
            }
        }

        public void add(int n) {
            if (queue.size() < k) {
                queue.offer(n);
            } else if (queue.peek() < n) {
                queue.poll();
                queue.offer(n);
            }
        }

        public int kthLargest() {
            return queue.peek();
        }
    }

    /**
     * 有效字母异位词
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isAnagram1(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> charCount = new HashMap<>();
        for (char ch : s.toCharArray()) {
            charCount.put(ch, charCount.getOrDefault(ch, 0) + 1);
        }
        for (char ch : t.toCharArray()) {
            charCount.put(ch, charCount.getOrDefault(ch, 0) - 1);
            if (charCount.get(ch) < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnagram(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        char[] sChar = s.toCharArray();
        char[] tChar = t.toCharArray();
        Arrays.sort(sChar);
        Arrays.sort(tChar);
        return Arrays.equals(sChar, tChar);
    }

    /**
     * 滑动窗口
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return new int[0];
        }
        int len = nums.length, index = 0;
        int[] ans = new int[len - k + 1];
        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            while (!queue.isEmpty() && queue.peekLast() <= nums[i]) {
                queue.pollLast();
            }
            queue.offer(i);
            if (queue.size() > k) {
                queue.poll();
            }
            if (i >= k - 1) {
                ans[index++] = nums[queue.peek()];
            }
        }
        return ans;
    }

    public static int[] maxSlidingWindow1(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return new int[0];
        }
        int len = nums.length, index = 0;
        int[] ans = new int[len - k + 1];
        Queue<Integer> queue = new PriorityQueue<>(k, (v1, v2) -> v2 - v1);
        for (int i = 0; i < len; i++) {
            if (queue.size() >= k) {
                queue.remove(nums[i - k]);
            }
            queue.offer(nums[i]);
            if (i >= k - 1) {
                ans[index++] = queue.peek();
            }
        }
        return ans;
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return new int[0];
        }
        int[] ans = new int[nums.length - k + 1];
        for (int i = 0, len = ans.length; i < len; i++) {
            int max = nums[i];
            for (int j = i + 1; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }
            ans[i] = max;
        }
        return ans;
    }

    public static class MyQueue {
        private Deque<Integer> stack1;
        private Deque<Integer> stack2;

        public MyQueue() {
            stack1 = new LinkedList<>();
            stack2 = new LinkedList<>();
        }

        // 先进先出

        public void offer(int value) {
            stack1.push(value);
        }

        public int poll() {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.poll());
            }
            return stack2.pop();
        }
    }

    private static class MyStack {
        private Queue<Integer> queue1 = new LinkedList<>();
        private Queue<Integer> queue2 = new LinkedList<>();

        // 后进先出

        public void push(int value) {
            queue2.offer(value);
            while (!queue1.isEmpty()) {
                queue2.offer(queue1.poll());
            }
            Queue<Integer> temp = queue1;
            queue1 = queue2;
            queue2 = temp;
        }

        public int pop() {
            return queue1.poll();
        }
    }

    /**
     * 有效括号
     *
     * @param s
     * @return
     */
    public static boolean isValid1(String s) {
        if (s == null || (s.length() & 1) == 1) {
            return false;
        }
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        Deque<Character> stack = new LinkedList<>();
        for (char ch : s.toCharArray()) {
            if (map.containsKey(ch)) {
                stack.addLast(ch);
            } else if (stack.isEmpty() || !Objects.equals(ch, map.get(stack.removeLast()))) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    public static boolean isValid(String s) {
        if (s == null || (s.length() & 1) == 1) {
            return false;
        }
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        Deque<Character> stack = new LinkedList<>();
        for (char ch : s.toCharArray()) {
            if (map.containsKey(ch)) {
                if (stack.isEmpty() || !Objects.equals(stack.peek(), map.get(ch))) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return true;
    }

    /**
     * 找出给定数组中和为0的所有不重复的四个元素组合
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> fourSum(int[] nums) {
        if (nums == null || nums.length < 4) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0, len = nums.length; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < len; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int left = j + 1, right = len - 1;
                while (left < right) {
                    int value = nums[i] + nums[j] + nums[left] + nums[right];
                    if (value > 0) {
                        right--;
                    } else if (value < 0) {
                        left++;
                    } else {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        left++;
                        right--;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 找出给定数组中三数之和为0 的不重复元素
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum2(int[] nums) {
        if (nums == null || nums.length < 3) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        Set<Integer> existSet = null;
        for (int i = 0, len = nums.length; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            existSet = new HashSet<>();
            for (int j = i + 1; j < len; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int c = -(nums[i] + nums[j]);
                if (existSet.contains(nums[j])) {
                    result.add(Arrays.asList(nums[i], c, nums[j]));
                } else {
                    existSet.add(c);
                }
            }
        }
        return result;
    }

    public static List<List<Integer>> threeSum1(int[] nums) {
        if (nums == null || nums.length < 3) {
            return Collections.emptyList();
        }
        // 降维 O(nlogn)
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0, len = nums.length; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1, right = len - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                }
            }
        }
        return result;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0, len = nums.length; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < len; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                for (int k = j + 1; k < len; k++) {
                    if (k > j + 1 && nums[k] == nums[k - 1]) {
                        continue;
                    }
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    }
                }
            }
        }
        return result;
    }

    /**
     * 两数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum1(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0, len = nums.length; i < len; i++) {
            int value = target - nums[i];
            if (map.containsKey(value)) {
                return new int[] {map.get(value), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }

    public static int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }
        for (int i = 0, len = nums.length; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[0];
    }
}
