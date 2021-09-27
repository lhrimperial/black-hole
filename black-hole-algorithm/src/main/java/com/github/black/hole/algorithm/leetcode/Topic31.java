package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/9/24
 */
public class Topic31 {

    /**
     * 31. 下一个排列 实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列（即，组合出下一个更大的整数）。
     *
     * <p>如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
     *
     * <p>必须 原地 修改，只允许使用额外常数空间。
     *
     * <p>
     *
     * <p>示例 1：
     *
     * <p>输入：nums = [1,2,3] 输出：[1,3,2]
     */
    public static void main(String[] args) {
        int[] arr = {4, 5, 2, 6, 3, 1};
        nextPermutation(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void nextPermutation(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        // 寻找一个较小数
        int len = nums.length, i = len - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        // 找到了一个较小数，寻找一个较大数
        if (i > 0) {
            int j = len - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    public static void reverse(int[] nums, int begin) {
        int left = begin, right = nums.length - 1;
        while (left < right) {
            swap(nums, left++, right--);
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
