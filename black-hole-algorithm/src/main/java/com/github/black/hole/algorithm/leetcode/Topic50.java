package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/2/17
 */
public class Topic50 {

    /**
     * 实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，xn）。
     *
     * <p>输入：x = 2.00000, n = 10 输出：1024.00000
     */
    public static void main(String[] args) {
        System.out.println(myPow1(2, -2147483648));
    }

    private static double quickMul(double x, long N) {
        if (N == 0) {
            return 1.0;
        }
        double y = quickMul(x, N / 2);
        return N % 2 == 0 ? y * y : y * y * x;
    }

    private static double quickMul1(double x, int N) {
        double ans = 1.0;
        // 贡献的初始值为 x
        double x_contribute = x;
        // 在对 N 进行二进制拆分的同时计算答案
        while (N > 0) {
            if (N % 2 == 1) {
                // 如果 N 二进制表示的最低位为 1，那么需要计入贡献
                ans *= x_contribute;
            }
            // 将贡献不断地平方
            x_contribute *= x_contribute;
            // 舍弃 N 二进制表示的最低位，这样我们每次只要判断最低位即可
            N /= 2;
        }
        return ans;

    }

    public static double myPow1(double x, int n) {
        return n > 0 ? quickMul1(x, n) : 1.0 / quickMul1(x, -n);
    }

    public static double myPow(double x, int n) {
        if (n == 0) {
            return 1.0;
        }
        if (n < 0) {
            return 1.0 / myPow(x, -n);
        }
        double y = myPow(x, n / 2);
        return (n & 1) == 1 ? x * y * y : y * y;
    }
}
