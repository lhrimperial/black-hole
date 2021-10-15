package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/10/8
 */
public class Topic26 {

    /** 26. 删除有序数组中的重复项 */
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 3, 4, 5, 5, 6, 7, 7, 7};
        System.out.println(removeDuplicates(nums));
        System.out.println(Arrays.toString(nums));
    }

    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length, left = 1, right = 1;
        while (right < len) {
            if (nums[right] != nums[right - 1]) {
                nums[left] = nums[right];
                left++;
            }
            right++;
        }
        return left;
    }
}
