package com.github.black.hole.algorithm.training;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/8/22
 */
public class Chapter23 {

    /** 169 求众数 */
    public static void main(String[] args) {
        int[] arr = {2, 2, 1, 1, 1, 2, 2};
        int result = majorityElement(arr);
        System.out.println(result);

        int[] arr1 = {2, 2, 1, 1, 1, 2, 1, 12};
        int result1 = majorityElement1(arr1);
        System.out.println(result1);
    }

    public static int majorityElement1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max_num = arr[0], count = 1;
        for (int i = 1, len = arr.length; i < len; i++) {
            if (max_num == arr[i]) {
                count++;
            } else if (--count == 0) {
                max_num = arr[i];
                count = 1;
            }
        }
        return max_num;
    }

    public static int majorityElement(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        Map.Entry<Integer, Integer> result = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (result == null || entry.getValue() > result.getValue()) {
                result = entry;
            }
        }
        return result.getKey();
    }
}
