package com.github.black.hole.algorithm.leetcode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/2/17
 */
public class Topic169 {

    /**
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     *
     * <p>你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     *
     * <p>输入：[2,2,1,1,1,2,2] 输出：2
     */
    public static void main(String[] args) {
        int[] arr = {2, 2, 1, 1, 1, 2, 2};
        int result = majorityElement1(arr);
        System.out.println(result);
    }

    /**
     * 摩尔投票法思路，多数元素>n/2, 则其他元素个数小于n/2
     *
     * @param nums
     * @return
     */
    public static int majorityElement1(int[] nums) {
        int cand_num = nums[0], count = 1;
        for (int i = 1, len = nums.length; i < len; i++) {
            if (cand_num == nums[i]) {
                count++;
            } else if (--count == 0) {
                cand_num = nums[i];
                count = 1;
            }
        }
        return cand_num;
    }

    /**
     * map 计数
     *
     * @param nums
     * @return
     */
    public static int majorityElement(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int n : nums) {
            if (countMap.containsKey(n)) {
                countMap.put(n, countMap.get(n) + 1);
            } else {
                countMap.put(n, 1);
            }
        }
        Map.Entry<Integer, Integer> result = null;
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (result == null || entry.getValue() > result.getValue()) {
                result = entry;
            }
        }
        return result.getKey();
    }
}
