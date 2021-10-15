package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/4
 */
public class Topic7 {
    /** 7. 整数反转 */
    public static void main(String[] args) {
        System.out.println(reverse(123456));
        System.out.println(reverse(-123456));
    }

    public static int reverse(int x) {
        int res = 0;
        while (x != 0) {
            int temp = res * 10 + x % 10;
            if (temp / 10 != res) {
                return 0;
            }
            res = temp;
            x /= 10;
        }
        return res;
    }
}
