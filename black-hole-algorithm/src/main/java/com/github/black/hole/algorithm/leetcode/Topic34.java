package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/10/11
 */
public class Topic34 {

    /** 34. 在排序数组中查找元素的第一个和最后一个位置 */
    public static void main(String[] args) {
        int[] nums = {1};
        int target = 1;
        System.out.println(Arrays.toString(searchRange(nums, target)));
    }

    public static int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[] {-1, -1};
        }
        int l = search(nums, target, true);
        int r = search(nums, target, false);
        if (l <= r && r < nums.length && nums[l] == target && nums[r] == target) {
            return new int[] {l, r};
        }
        return new int[] {-1, -1};
    }

    public static int search(int[] nums, int target, boolean lower) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] > target || (lower && nums[mid] >= target)) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return lower ? l : r;
    }
}
