package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/11
 */
public class Topic35 {

    /** 35. 搜索插入位置 */
    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 6};
        int target = 7;
        System.out.println(searchInsert(nums, target));
        System.out.println(searchInsert1(nums, target));
    }

    public static int searchInsert1(int[] nums, int target) {
        int n = nums.length;
        int left = 0, right = n - 1, ans = n;
        while (left <= right) {
            int mid = ((right - left) >> 1) + left;
            if (target <= nums[mid]) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    public static int searchInsert(int[] nums, int target) {
        int len = nums.length, l = 0, r = len - 1, ans = len;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] <= target) {
                l = mid + 1;
            } else {
                ans = mid;
                r = mid - 1;
            }
        }
        return ans;
    }
}
