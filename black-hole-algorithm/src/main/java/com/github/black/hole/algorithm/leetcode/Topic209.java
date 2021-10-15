package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/11
 */
public class Topic209 {
    /** 209. 长度最小的子数组 */
    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 2, 4, 3};
        System.out.println(minSubArrayLen(7, arr));
    }

    public static int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length, left = 0, right = 0, sum = 0, ans = Integer.MAX_VALUE;
        while (right < len) {
            sum += nums[right++];
            while (sum >= target) {
                ans = Math.min(ans, right - left);
                sum -= nums[left++];
            }
        }
        return ans;
    }
}
