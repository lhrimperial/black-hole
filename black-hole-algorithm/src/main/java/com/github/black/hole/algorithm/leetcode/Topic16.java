package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/10/10
 */
public class Topic16 {

    /** 16. 最接近的三数之和 */
    public static void main(String[] args) {
        int[] nums = {-1, 2, 1, -4};
        int target = 1;
        System.out.println(threeSumClosest(nums, target));
    }

    public static int threeSumClosest(int[] nums, int target) {
        int len = nums.length, bast = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for (int i = 0; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int l = i + 1, r = len - 1;
            while (l < r) {
                int s = nums[i] + nums[l] + nums[r];
                if (s == target) {
                    return s;
                }
                if (Math.abs(s - target) < Math.abs(bast - target)) {
                    bast = s;
                }
                if (s > target) {
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    r--;
                } else {
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    l++;
                }
            }
        }
        return bast;
    }
}
