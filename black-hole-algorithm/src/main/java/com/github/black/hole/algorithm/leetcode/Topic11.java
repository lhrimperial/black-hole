package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/10
 */
public class Topic11 {

    /**
     * 11. 盛最多水的容器
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxArea(arr));
    }

    public static int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int l = 0, r = height.length - 1, ans = 0;
        while (l < r) {
            int area = Math.min(height[l], height[r]) * (r - l);
            ans = Math.max(ans, area);
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return ans;
    }
}
