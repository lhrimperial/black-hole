package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic416 {

    /** 第 416 题：分割等和子集（中等） */
    public static void main(String[] args) {
        System.out.println(canPartition(new int[]{1,5,11,5}));
    }

    public static boolean canPartition(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        int sum = Arrays.stream(nums).sum();
        //和未奇数不满足
        if ((sum & 1) == 1) {
            return false;
        }

        int target = sum >> 1, len = nums.length;
        //len 个数 target 填满
        boolean[][] dp = new boolean[len][target + 1];
//        if (nums[0] <= target) {
//            dp[0][nums[0]] = true;
//        }
        for (int i = 1; i < len; i++) {
            for (int j = 0; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];
                if (nums[i] == j) {
                    dp[i][j] = true;
                }
                if (nums[i] < j) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[len - 1][target];
    }
}
