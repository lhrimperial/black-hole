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
        int[] arr = {1, 2, 4, 8};
        int aa = Arrays.stream(arr).reduce(0, (t1, t2) -> t1 | t2);
        System.out.println(aa);
    }


    /**
     * 有效括号
     *
     * @param s
     * @return
     */
    public static boolean isValid(String s) {
        if (s == null || (s.length() & 1) == 1) {
            return false;
        }
        Map<Character, Character> pair = new HashMap<>();
        pair.put(')', '(');
        pair.put(']', '[');
        pair.put('}', '{');
        Deque<Character> stack = new ArrayDeque<>();
        for (Character ch : s.toCharArray()) {
            if (pair.containsKey(ch)) {
                if (stack.isEmpty() || !Objects.equals(pair.get(ch), stack.peek())) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }

    /**
     * 找出数组中四数之和为零的所有组合
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
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < len; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int l = j + 1, r = len - 1;
                while (l < r) {
                    int s = nums[i] + nums[j] + nums[l] + nums[r];
                    if (s < 0) {
                        l++;
                    } else if (s > 0) {
                        r--;
                    } else {
                        result.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        while (l < r && nums[r] == nums[r - 1]) {
                            r--;
                        }
                        r--;
                        l++;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 找出数组中三数之和为零的所有组合
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
        for (int i = 0, len = nums.length; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int l = i + 1, r = len - 1;
            while (l < r) {
                int s = nums[i] + nums[l] + nums[r];
                if (s < 0) {
                    l++;
                } else if (s > 0) {
                    r--;
                } else {
                    result.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    while (l < r && nums[l + 1] == nums[l]) {
                        l++;
                    }
                    while (l < r && nums[r - 1] == nums[r]) {
                        r--;
                    }
                    l++;
                    r--;
                }
            }
        }
        return result;
    }

    public static List<List<Integer>> threeSum1(int[] nums) {
        if (nums == null || nums.length < 3) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        Set<List<Integer>> result = new HashSet<>();

        Set<Integer> existSet = null;
        for (int i = 0, len = nums.length; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            existSet = new HashSet<>();
            for (int j = i + 1; j < len; j++) {
                int c = -(nums[i] + nums[j]);
                if (!existSet.contains(nums[j])) {
                    existSet.add(c);
                } else {
                    result.add(Arrays.asList(nums[i], c, nums[j]));
                }
            }
        }
        return new ArrayList<>(result);
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
     * @param arr
     * @param target
     * @return
     */
    public static int[] twoSum1(int[] arr, int target) {
        if (arr == null || arr.length < 2) {
            return new int[0];
        }
        Map<Integer, Integer> valueIndexMap = new HashMap<>();
        for (int i = 0, len = arr.length; i < len; i++) {
            int value = target - arr[i];
            if (valueIndexMap.containsKey(value)) {
                return new int[] {valueIndexMap.get(value), i};
            }
            valueIndexMap.put(arr[i], i);
        }
        return new int[0];
    }

    public static int[] twoSum(int[] arr, int target) {
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
