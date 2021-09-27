package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hairen.long
 * @date 2021/9/25
 */
public class Topic41 {

    /**
     * 41. 缺失的第一个正数
     *
     * <p>给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
     *
     * <p>请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
     */
    public static void main(String[] args) {
        int[] nums = {3, 4, -1, 1};
        System.out.println(firstMissingPositive1(nums));
    }

    public static int firstMissingPositive1(int[] nums) {
        if (nums == null) {
            return 0;
        }

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, nums[i] - 1, i);
            }
        }
        System.out.println(Arrays.toString(nums));
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }

    private static void swap(int[] arr, int x, int y) {
        int t = arr[y];
        arr[y] = arr[x];
        arr[x] = t;
    }

    public static int firstMissingPositive(int[] nums) {
        if (nums == null) {
            return 0;
        }
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int len = nums.length;
        for (int i = 1; i <= len; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
        return len + 1;
    }
}
