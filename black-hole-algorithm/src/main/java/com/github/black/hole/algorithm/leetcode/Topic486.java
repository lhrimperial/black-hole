package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/3
 */
public class Topic486 {
    /** 486. 预测赢家 */
    public static void main(String[] args) {
        int[] nums = {1,5,233,7};
        System.out.println(predictTheWinner(nums));
    }

    public static boolean predictTheWinner(int[] nums) {
        if (nums == null || nums.length < 2) {
            return true;
        }
        int len = nums.length, left = 0, right = len - 1;
        int a = 0, b = 0;
        while (left <= right) {
            if (nums[left] < nums[right]) {
                a += nums[right];
                right--;
            } else {
                a += nums[left];
                left++;
            }
            if (nums[left] < nums[right]) {
                b += nums[right];
                right--;
            } else {
                b += nums[left];
                left++;
            }
        }
        return a > b;
    }
}
