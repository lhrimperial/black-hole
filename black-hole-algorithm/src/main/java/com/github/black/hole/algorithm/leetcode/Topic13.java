package com.github.black.hole.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/10/10
 */
public class Topic13 {

    /** 13. 罗马数字转整数 */
    public static void main(String[] args) {
        String value = "LVIII";
        System.out.println(romanToInt(value));
    }

    public static int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int ans = 0, len = s.length();
        for (int i = 0; i < len; i++) {
            int value = map.get(s.charAt(i));
            if (i < len - 1 && value < map.get(s.charAt(i + 1))) {
                ans -= value;
            } else {
                ans += value;
            }
        }
        return ans;
    }
}
