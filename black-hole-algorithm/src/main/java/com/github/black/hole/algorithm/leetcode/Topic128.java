package com.github.black.hole.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hairen.long
 * @date 2021/9/29
 */
public class Topic128 {
    /**
     * 128. 最长连续序列 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     *
     * <p>请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
     */
    public static void main(String[] args) {
        int[] nums = {100, 4, 200, 1, 3, 2};
        System.out.println(longestConsecutive(nums));
    }

    public static int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Set<Integer> hash = new HashSet<>();
        for (int num : nums) {
            hash.add(num);
        }
        int ans = -1;
        for (int x : nums) {
            // 不是一个连续序列的开头
            if (!hash.contains(x - 1)) {
                int y = x;
                while (hash.contains(y + 1)) {
                    y++;
                }
                ans = Math.max(ans, y - x + 1);
            }
        }
        return ans;
    }
}
