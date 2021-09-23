package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/21
 */
public class Topic704 {

    /** 704. 二分查找 */
    public static void main(String[] args) {
        int[] nums = {-1, 0, 3, 3, 3, 5, 9, 12};
        System.out.println(leftSearch(nums, 3));
        System.out.println(rightSearch(nums, 3));
    }

    public static int rightSearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else if (arr[mid] == target) {
                left = mid + 1;
            }
        }
        if (right < 0 || arr[right] != target) {
            return -1;
        }
        return right;
    }

    public static int leftSearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else if (arr[mid] == target) {
                right = mid - 1;
            }
        }
        if (left > arr.length || arr[left] != target) {
            return -1;
        }
        return left;
    }

    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
        }
        return -1;
    }
}
