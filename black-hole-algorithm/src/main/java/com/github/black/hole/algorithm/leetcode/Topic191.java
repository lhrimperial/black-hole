package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/8/25
 */
public class Topic191 {

    /** 位1的个数 */
    public static void main(String[] args) {
        System.out.println(hammingWeight(8));
        System.out.println(hammingWeight(14));
        System.out.println(hammingWeight(15));
        System.out.println(hammingWeight1(15));
    }

    public static int hammingWeight1(int n) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & (1 << i)) != 0) {
                count++;
            }
        }
        return count;
    }

    public static int hammingWeight(int n) {
        int count = 0;
        while (n > 0) {
            count++;
            n &= (n - 1);
        }
        return count;
    }
}
