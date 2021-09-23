package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/22
 */
public class Topic198 {

    /**
     * 198. 打家劫舍
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     *
     * <p>给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     */
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 1};
        System.out.println(rob(arr));
        System.out.println(rob1(arr));
    }

    public static int rob1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int pre = 0, curr = 0, temp = 0;
        for (int n : nums) {
            temp = curr;
            curr = Math.max(n + pre, curr);
            pre = temp;
        }
        return curr;
    }

    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[n - 1];
    }
}
