package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/8/25
 */
public class Topic231 {

    /** 2的幂 */
    public static void main(String[] args) {
        System.out.println(isPowerOfTwo1(8));
        System.out.println(isPowerOfTwo1(9));
        System.out.println(isPowerOfTwo2(8));
        System.out.println(isPowerOfTwo2(9));
    }

    /**
     * 获取最低位
     *
     * @param x
     * @return
     */
    public static boolean isPowerOfTwo2(int x) {
        return x > 0 && (x & -x) == x;
    }

    /**
     * 清除最低位
     *
     * @param x
     * @return
     */
    public static boolean isPowerOfTwo1(int x) {
        return x > 0 && (x & (x - 1)) == 0;
    }
}
