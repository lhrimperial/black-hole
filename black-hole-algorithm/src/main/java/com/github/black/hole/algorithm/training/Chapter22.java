package com.github.black.hole.algorithm.training;

/**
 * @author hairen.long
 * @date 2021/8/22
 */
public class Chapter22 {

    /** pow(x,y) */
    public static void main(String[] args) {
        double result = myPow(2, 4);
        System.out.println(result);
    }

    public static double myPow(double x, int n) {
        if (n == 0) {
            return 1.0;
        }
        if (n < 0) {
            return 1 / myPow(x, -n);
        }
        double y = myPow(x, n / 2);
        return (n & 1) == 1 ? x * y * y : y * y;
    }
}
