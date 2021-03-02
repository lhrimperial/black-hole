package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/2/9
 */
public class Topic1 {

    /**
     * 查找数组中两数之后等于目标值的下标
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = new int[] {2, 7, 11, 15};
        System.out.println(Arrays.toString(solution1(arr, 9)));
        System.out.println(Arrays.toString(solution2(arr, 9)));
    }

    public static int[] solution2(int[] arr, int target) {
        if (arr.length < 2) {
            return new int[0];
        }
        Map<Integer, Integer> valueIndex = new HashMap<>();
        for (int i = 0, len = arr.length; i < len; i++) {
            int value = target - arr[i];
            if (valueIndex.containsKey(value)) {
                return new int[] {i, valueIndex.get(value)};
            }
            valueIndex.put(arr[i], i);
        }
        return new int[0];
    }

    public static int[] solution1(int[] arr, int target) {
        if (arr.length < 2) {
            return new int[0];
        }
        for (int i = 0, len = arr.length; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[0];
    }
}
