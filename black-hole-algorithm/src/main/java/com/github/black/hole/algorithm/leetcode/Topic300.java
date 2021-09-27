package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/9/24
 */
public class Topic300 {

    /**
     * 300. 最长递增子序列 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     *
     * <p>子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
     */
    public static void main(String[] args) {
        int[] arr = {0, 3, 1, 6, 2, 2, 7};
        System.out.println(lengthOfLIS(arr));
        System.out.println(lengthOfLIS1(arr));
    }



    public static int lengthOfLIS1(int[] nums) {
        if (nums == null || nums.length == 0){
            return 0;
        }
        int len = nums.length, res = 0;
        //保存满足条件的子序列
        int[] se = new int[len];
        for (int num : nums) {
            //二分查找num在子序列中的位置
            int i = 0, j = res;
            while (i < j) {
                int mid = (i + j) >> 1;
                if (se[mid] < num) {
                    i = mid + 1;
                } else {
                    j = mid;
                }
            }
            se[i] = num;
            if (j == res) {
                res++;
            }
        }
        return res;
    }

    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int ans = 0, n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
