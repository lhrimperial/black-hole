package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/4
 */
public class Topic9 {
    /** 9. 回文数 */
    public static void main(String[] args) {
        System.out.println(isPalindrome(12321));
    }

    public static boolean isPalindrome(int x) {
        //负数 和 首位不能是0
        if (x < 0 || x % 10 == 0 && x != 0) {
            return false;
        }
        int rev = 0;
        while (x > rev) {
            rev = rev * 10 + x % 10;
            x /= 10;
        }
        return x == rev || x == rev / 10;
    }
}
