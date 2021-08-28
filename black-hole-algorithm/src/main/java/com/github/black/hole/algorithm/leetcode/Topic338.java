package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/8/25
 */
public class Topic338 {

    /** 比特位计数 */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(countBits1(9)));
        System.out.println(Arrays.toString(countBits2(9)));
        System.out.println(Arrays.toString(countBits3(9)));
        System.out.println(Arrays.toString(countBits4(9)));
    }

    public static int[] countBits4(int n) {
        if (n < 0) {
            return new int[0];
        }
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            bits[i] = bits[i & (i - 1)] + 1;
        }
        return bits;
    }

    /**
     * 每位二进制1的个数等于右移一位下标位置的数+（奇数+1，偶数+0）
     *
     * @param n
     * @return
     */
    public static int[] countBits3(int n) {
        if (n < 0) {
            return new int[0];
        }
        int[] bits = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            bits[i] = bits[i >> 1] + (i & 1);
        }
        return bits;
    }

    /**
     * 最高有效位
     *
     * @param n
     * @return
     */
    public static int[] countBits2(int n) {
        if (n < 0) {
            return new int[0];
        }
        int highBit = 0;
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if ((i & (i - 1)) == 0) {
                highBit = i;
            }
            bits[i] = bits[i - highBit] + 1;
        }
        return bits;
    }

    /**
     * 数位数
     *
     * @param n
     * @return
     */
    public static int[] countBits1(int n) {
        if (n < 0) {
            return new int[0];
        }
        int[] bits = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            bits[i] = countOne(i);
        }

        return bits;
    }

    public static int countOne(int x) {
        int count = 0;
        while (x > 0) {
            count++;
            x &= (x - 1);
        }
        return count;
    }
}
