package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/2/14
 */
public class Topic18 {
    /**
     * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c和 d，使得 a + b + c + d
     * 的值与target相等？找出所有满足条件且不重复的四元组。
     *
     * <p>给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
     *
     * <p>满足要求的四元组集合为： [ [-1, 0, 0, 1], [-2, -1, 1, 2], [-2, 0, 0, 2] ]
     */
    public static void main(String[] args) {
        int[] arr = {1, 0, -1, 0, -2, 2};
        List<List<Integer>> result = fourSum(arr, 0);
        System.out.println(result.toString());
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums == null || nums.length < 4) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0, len = nums.length; i < len; i++) {
            if (nums[i] > target) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < len; j++) {
                if (nums[j] > target) {
                    break;
                }
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int l = j + 1, r = len - 1;
                while (l < r) {
                    int s = nums[i] + nums[j] + nums[l] + nums[r];
                    if (s < target) {
                        l++;
                    } else if (s > target) {
                        r--;
                    } else {
                        result.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        while (l < r && nums[r] == nums[r - 1]) {
                            r--;
                        }
                        l++;
                        r--;
                    }
                }
            }
        }
        return result;
    }
}
