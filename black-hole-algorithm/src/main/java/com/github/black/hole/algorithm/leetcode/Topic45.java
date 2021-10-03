package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/30
 */
public class Topic45 {
    /**
     * 45. 跳跃游戏 II
     *
     * <p>给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
     *
     * <p>数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *
     * <p>你的目标是使用最少的跳跃次数到达数组的最后一个位置。
     *
     * <p>假设你总是可以到达数组的最后一个位置。
     */
    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 2, 4, 2, 3};
        System.out.println(jump(nums));
    }

    public static int jump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length - 1;
        int step = 0, position = 0, end = 0;
        for (int i = 0; i < len; i++) {
            // 维护当前能够到达的最大下标位置，记为边界。我们从左到右遍历数组，到达边界时，更新边界并将跳跃次数增加 1
            position = Math.max(position, i + nums[i]);
            System.out.println("i=" + i + ",position:" + position + ",end=" + end);
            if (i == end) {
                end = position;
                step++;
            }
        }
        return step;
    }
}
