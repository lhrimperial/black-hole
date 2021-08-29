package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/8/28
 */
public class Topic509 {

    /**
     * 斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
     *
     * <p>F(0) = 0，F(1) = 1 F(n) = F(n - 1) + F(n - 2)，其中 n > 1
     */
    public static void main(String[] args) {
        System.out.println(fib2(2));
        System.out.println(fib(2));
        System.out.println(fib(4));
        System.out.println(fib1(4));
        System.out.println(fib2(4));
    }

    public static int fib2(int n) {
        if (n < 2) {
            return n;
        }
        int p = 0, q = 0, r = 1;
        for (int i = 2; i <= n; i++) {
            p = q;
            q = r;
            r = q + p;
        }
        return r;
    }

    public static int fib1(int n) {
        int[] f = new int[n + 1];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f[n];
    }

    public static int fib(int n) {
        if (n <= 1) {
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }
}
