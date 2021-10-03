package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/9/28
 */
public class Topic842 {
    /** 842. 将数组拆分成斐波那契序列 */
    public static void main(String[] args) {
        String s = "123456579";
        List<Integer> res = splitIntoFibonacci(s);
        System.out.println(res.toString());
    }

    public static List<Integer> splitIntoFibonacci(String num) {
        if (num == null || num.length() == 0) {
            return Collections.emptyList();
        }
        List<Integer> res = new ArrayList<>();
        dfs(num.toCharArray(), 0, res);
        return res;
    }

    private static boolean dfs(char[] digit, int index, List<Integer> res) {
        if (digit.length == index && res.size() >= 3) {
            return true;
        }
        int size = res.size(), len = digit.length;
        for (int i = index; i < len; i++) {
            if (i > index && digit[index] == '0') {
                break;
            }
            long curr = 0;
            for (int j = index; j <= i; j++) {
                curr = curr * 10 + (digit[j] - '0');
            }
            if (curr > Integer.MAX_VALUE) {
                break;
            }
            if (size >= 2 && curr > res.get(size - 1) + res.get(size - 2)) {
                break;
            }
            if (size <= 1 || curr == res.get(size - 1) + res.get(size - 2)) {
                res.add((int) curr);
                if (dfs(digit, i + 1, res)) {
                    return true;
                }
                res.remove(res.size() - 1);
            }
        }
        return false;
    }
}
