package com.github.black.hole.base.algorithm.leetcode;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2020/10/27
 */
public class Solution1 {

    /**
     * 给定一个整数数组 nums和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = {2, 7, 11, 15};
        int[] resp = twoSum(arr, 9);
        System.out.println(Arrays.toString(resp));
        resp = twoSumWay1(arr, 9);
        System.out.println(Arrays.toString(resp));
    }

    /**
     * key是数组元素，value是下标
     *
     * @param num
     * @param target
     * @return
     */
    public static int[] twoSumWay1(int[] num, int target) {
        Map<Integer, Integer> valueIndexMap = Maps.newHashMap();
        for (int i = 0, len = num.length; i < len; i++) {
            if (valueIndexMap.containsKey(target - num[i])) {
                return new int[]{valueIndexMap.get(target - num[i]), i};
            }
            valueIndexMap.put(num[i], i);
        }
        return new int[0];
    }

    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0, len = nums.length; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[0];
    }
}
