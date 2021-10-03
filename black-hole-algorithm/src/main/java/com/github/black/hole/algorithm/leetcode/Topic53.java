package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/29
 */
public class Topic53 {
    /** 53. 最大子序和 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。 */
    public static void main(String[] args) {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(arr));
    }

    public static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int pre = nums[0], ans = 0, len = nums.length;
        for (int i = 1; i < len; i++) {
            pre = Math.max(pre + nums[i], nums[i]);
            ans = Math.max(ans, pre);
        }
        return ans;
    }
}
