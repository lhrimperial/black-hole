package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/8/29
 */
public class Topic152 {
    /**
     * 你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
     *
     * <p>
     *
     * <p>示例 1:
     *
     * <p>输入: [2,3,-2,4] 输出: 6 解释: 子数组 [2,3] 有最大乘积 6。
     */
    public static void main(String[] args) {
        int[] arr = {-2, 0, -1};
        System.out.println(maxProduct(arr));
        System.out.println(maxProduct1(arr));
    }

    public static int maxProduct1(int[] nums) {
        int maxF = nums[0], minF = nums[0], ans = nums[0];
        int length = nums.length;
        for (int i = 1; i < length; ++i) {
            int mx = maxF, mn = minF;
            maxF = Math.max(mx * nums[i], Math.max(nums[i], mn * nums[i]));
            minF = Math.min(mn * nums[i], Math.min(nums[i], mx * nums[i]));
            ans = Math.max(maxF, ans);
        }
        return ans;
    }

    public static int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = nums[0], min = nums[0], ans = 0, len = nums.length;
        for (int i = 1; i < len; i++) {
            int mx = max, mn = min;
            max = Math.max(mx * nums[i], Math.max(mn * nums[i], nums[i]));
            min = Math.min(mx * nums[i], Math.min(mn * nums[i], nums[i]));
            ans = Math.max(max, ans);
        }
        return ans;
    }
}
