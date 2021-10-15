package com.github.black.hole.algorithm.leetcode;

import org.checkerframework.checker.units.qual.C;

/**
 * @author hairen.long
 * @date 2021/10/10
 */
public class Topic12 {

    /** 12. 整数转罗马数字 */
    public static void main(String[] args) {
        System.out.println(intToRoman(3));
    }

    static int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    static String[] symbol = {
        "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };

    public static String intToRoman(int num) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            int value = nums[i];
            while (num >= value) {
                num -= value;
                ans.append(symbol[i]);
            }
            if (num == 0) {
                break;
            }
        }
        return ans.toString();
    }
}
