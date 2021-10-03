package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/30
 */
public class Topic55 {
    /**
     * 55. 跳跃游戏 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
     *
     * <p>数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *
     * <p>判断你是否能够到达最后一个下标。
     */
    public static void main(String[] args) {
        int[] nums = {3, 2, 1, 0, 4};
        System.out.println(canJump(nums));
    }

    public static boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return true;
        }
        int len = nums.length - 1;
        int position = 0;
        for (int i = 0; i < len; i++) {
            if (i <= position) {
                position = Math.max(position, i + nums[i]);
                System.out.println("i=" + i + ",p=" + position);
                if (position >= len) {
                    return true;
                }
            }
        }
        return false;
    }
}
