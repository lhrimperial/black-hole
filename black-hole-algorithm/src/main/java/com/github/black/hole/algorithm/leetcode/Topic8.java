package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/8
 */
public class Topic8 {

    /** 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数） */
    public int myAtoi(String s) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        // 1.去空格
        int index = 0;
        while (index < len && chars[index] == ' ') {
            index++;
        }
        // 2.排除极端情况 "    "
        if (index == len) {
            return 0;
        }
        // 3.设置符号
        int sign = 1;
        char firstChar = chars[index];
        if (firstChar == '-') {
            index++;
            sign = -1;
        } else if (firstChar == '+') {
            index++;
        }
        int res = 0, last = 0;
        // last 记录上一次的res，以此来判断是否溢出
        while (index < len) {
            char c = chars[index];
            if (c < '0' || c > '9') {
                break;
            }
            int tem = c - '0';
            last = res;
            res = res * 10 + tem;
            // //如果不相等就是溢出了
            if (last != res / 10) {
                return (sign == (-1)) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            index++;
        }
        return res * sign;
    }
}
