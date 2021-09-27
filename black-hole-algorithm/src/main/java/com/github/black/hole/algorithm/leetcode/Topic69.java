package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/8/24
 */
public class Topic69 {

    /**
     * 实现 int sqrt(int x) 函数。
     *
     * <p>计算并返回 x 的平方根，其中 x 是非负整数。
     *
     * <p>由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
     */
    public static void main(String[] args) {
//        System.out.println(Math.sqrt(0));
//        System.out.println(Math.sqrt(1));
//        System.out.println(sqrt(9, 1e-7));
//        System.out.println(sqrt(5, 1e-7));
//        System.out.println(myDoubleSqrt(9, 1e-7));
//        System.out.println(myDoubleSqrt(2, 1e-7));
//        System.out.println(myDoubleSqrt(2, 1e-9));
        System.out.println(myIntSqrt(8));
    }

    public static int myIntSqrt(int x) {
        if (x <= 1) {
            return x;
        }
        int l = 1, h = x;
        while (l <= h) {
            int mid = l + (h - l) / 2;
            int sqrt = x / mid;
            if (mid == sqrt) {
                return mid;
            } else if (mid > sqrt) {
                h = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return h;
    }

    public static double myDoubleSqrt(int x, double scale) {
        if (x < 0) {
            throw new RuntimeException("");
        }
        if (x <= 0) {
            return x;
        }
        int i = 0;
        for (; i < x; i++) {
            int squre = i * i;
            if (squre == x) {
                return squre;
            }
            if (squre > x) {
                break;
            }
        }

        double low = i - 1, high = i, squre, mid;
        while (high - low > scale) {
            mid = low + (high - low) / 2;
            squre = mid * mid;
            if (squre > x) {
                high = mid;
            } else {
                low = mid;
            }
        }
        System.out.println("high:" + high + ",low=" + low);
        return (high + low) / 2;
    }

    public static double sqrt(int x, Double precise) {
        if (x <= 1) {
            return x;
        }

        // 先确定当前数所处的最小整数区间
        int i = 0;
        for (; i <= x; i++) {
            if (i * i == x) {
                return i;
            }
            if (i * i > x) {
                break;
            }
        }

        // 再通过二分法来进行判断检测
        double low = i - 1, high = i, prec = precise != null ? precise : 1e-7;
        double middle, squre;
        while (high - low > prec) {
            middle = (low + high) / 2;
            squre = middle * middle;

            if (squre > x) {
                high = middle;
            } else {
                low = middle;
            }
        }
        return (low + high) / 2;
    }
}
