package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic42 {
    /** 42. 接雨水 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。 */
    public static void main(String[] args) {
        int[] arr = {4, 2, 0, 3, 2, 5};
        System.out.println(trap(arr));
        System.out.println(trap1(arr));
    }

    public static int trap1(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int ans = 0;
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (height[left] < height[right]) {
                ans += leftMax - height[left];
                left++;
            } else {
                ans += rightMax - height[right];
                right--;
            }
        }
        return ans;
    }

    public static int trap(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        int len = height.length;
        int[] left = new int[len], right = new int[len];
        left[0] = height[0];
        right[len - 1] = height[len - 1];
        for (int i = 1; i < len; i++) {
            left[i] = Math.max(left[i - 1], height[i]);
        }
        for (int i = len - 2; i >= 0; i--) {
            right[i] = Math.max(right[i + 1], height[i]);
        }
        int sum = 0;
        for (int i = 1; i < len - 1; i++) {
            int min = Math.min(left[i], right[i]);
            if (min > height[i]) {
                sum += (min - height[i]);
            }
        }
        return sum;
    }
}
