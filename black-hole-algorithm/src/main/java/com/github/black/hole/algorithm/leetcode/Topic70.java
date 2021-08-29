package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/8/28
 */
public class Topic70 {
    /**
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     *
     * <p>每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     *
     * <p>注意：给定 n 是一个正整数。
     */
    public static void main(String[] args) {
        System.out.println(climbStairs(3));
    }

    public static int climbStairs(int n) {
        if (n < 1) {
            return 1;
        }
        int p = 0, q = 1, r = p + q;
        for (int i = 2; i <= n; i++) {
            p = q;
            q = r;
            r = q + p;
        }
        return r;
    }
}
