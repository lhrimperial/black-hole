package com.github.black.hole.base.algorithm.dynamic;

/**
 * @author hairen.long
 * @date 2020/10/27
 */
public class Fibonacci {

    public static void main(String[] args) {
        int result = fibWithMemo(6);
        System.out.println("result=" + result);
    }

    /**
     * 递归
     *
     * @param n
     * @return
     */
    public static int fib(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    /**
     * 自顶向下的备忘录法
     *
     * @param n
     * @return
     */
    public static int fibWithMemo(int n) {
        if (n <= 0) {
            return n;
        }
        int[] memo = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            memo[i] = -1;
        }
        return fib(n, memo);
    }

    public static int fib(int n, int[] memo) {

        if (memo[n] != -1) {
            return memo[n];
        }
        //如果已经求出了fib（n）的值直接返回，否则将求出的值保存在Memo备忘录中。
        if (n <= 2) {
            memo[n] = 1;
        } else {
            memo[n] = fib(n - 1, memo) + fib(n - 2, memo);
        }

        return memo[n];
    }

    /**
     * 自底向上的动态规划
     *
     * @param n
     * @return
     */
    public static int fibWithBottom(int n) {
        if (n <= 0) {
            return n;
        }
        int[] memo = new int[n + 1];
        memo[0] = 0;
        memo[1] = 1;
        for (int i = 2; i <= n; i++) {
            memo[i] = memo[i - 1] + memo[i - 2];
        }
        return memo[n];
    }
}
