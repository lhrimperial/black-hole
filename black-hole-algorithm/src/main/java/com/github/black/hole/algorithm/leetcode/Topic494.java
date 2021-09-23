package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic494 {
    /**
     * 494. 目标和
     *
     * <p>给你一个整数数组 nums 和一个整数 target 。
     *
     * <p>向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
     *
     * <p>例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。 返回可以通过上述方法构造的、运算结果等于
     * target 的不同 表达式 的数目。
     */
    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 1, 1};
        System.out.println(findTargetSumWays(arr, 3));
        System.out.println(findTargetSumWays1(arr, 3));
    }

    public static int findTargetSumWays1(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = Arrays.stream(nums).sum();
        int diff = sum - target;
        if (diff < 0 || (diff & 1) == 1) {
            return 0;
        }
        int n = nums.length, neg =  diff >> 1;
        int[][] dp = new int[n + 1][neg + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            int num = nums[i - 1];
            for (int j = 0; j <= neg; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= num) {
                    dp[i][j] += dp[i - 1][j - num];
                }
            }
        }
        return dp[n][neg];
    }

    public static int findTargetSumWays(int[] nums, int target) {
        backtrack(nums, target, 0, 0);
        return count;
    }

    private static int count;

    private static void backtrack(int[] nums, int target, int index, int sum) {
        if (index == nums.length) {
            if (target == sum) {
                count++;
            }
        } else {
            backtrack(nums, target, index + 1, sum + nums[index]);
            backtrack(nums, target, index + 1, sum - nums[index]);
        }
    }
}
