package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/2
 */
public class Topic263 {

    /** 263. 丑数 */
    public static void main(String[] args) {
        System.out.println(isUgly(6));
        System.out.println(isUgly(14));
    }

    public static boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        int[] fac = {2, 3, 5};
        for (int fa : fac) {
            while (n % fa == 0) {
                n /= fa;
            }
        }
        return n == 1;
    }
}
