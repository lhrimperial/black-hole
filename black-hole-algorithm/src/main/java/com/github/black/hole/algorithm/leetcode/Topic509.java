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
        System.out.println(fib2(5));
        System.out.println(fib11(5));
        System.out.println(fib12(5));
        System.out.println(fib13(5));
    }

    public static int fib13(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int pre = 1, curr = 1, s = 0;
        for (int i = 3; i <= n; i++) {
            s = pre + curr;
            pre = curr;
            curr = s;
        }
        return curr;
    }

    public static int fib12(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[] memo = new int[n + 1];
        memo[0] = 0;
        memo[1] = 1;
        for (int i = 2; i <= n; i++) {
            memo[i] = memo[i - 1] + memo[i - 2];
        }
        return memo[n];
    }

    public static int fib11(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return fib11(n - 1) + fib11(n - 2);
    }

    public static int fib2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
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
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[] f = new int[n + 1];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f[n];
    }

    public static int fib(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }
}
