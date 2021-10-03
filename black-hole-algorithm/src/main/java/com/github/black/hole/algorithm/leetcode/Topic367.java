package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/3
 */
public class Topic367 {
    /** 367. 有效的完全平方数 */
    public static void main(String[] args) {
        System.out.println(isPerfectSquare(808201));
        System.out.println(isPerfectSquare1(808201));
    }

    public static boolean isPerfectSquare(int num) {
        if (num < 2) {
            return true;
        }
        long left = 2, right = num / 2, mid, s;
        while (left <= right) {
            mid = left + (right - left) / 2;
            s = mid * mid;
            if (s == num) {
                return true;
            }
            if (s > num) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }

    public static boolean isPerfectSquare1(int num) {
        if (num < 2) {
            return true;
        }

        long left = 2, right = num / 2, x, guessSquared;
        while (left <= right) {
            x = left + (right - left) / 2;
            guessSquared = x * x;
            if (guessSquared == num) {
                return true;
            }
            if (guessSquared > num) {
                right = x - 1;
            } else {
                left = x + 1;
            }
        }
        return false;
    }
}
