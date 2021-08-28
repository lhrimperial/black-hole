package com.github.black.hole.algorithm.leetcode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hairen.long
 * @date 2021/2/21
 */
public class Topic52 {

    /**
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     *
     * <p>给你一个整数 n ，返回 n 皇后问题 不同的解决方案的数量。
     */
    public static void main(String[] args) {
        System.out.println(countNQueue(4));
        System.out.println(countNQueue(4));
    }

    public static int countNQueue(int n) {
        if (n <= 0) {
            return 0;
        }
        return dfsCountNQueue(n, 0, 0, 0, 0);
    }

    public static int dfsCountNQueue(int n, int row, int col, int pos, int neg) {
        if (n == row) {
            return 1;
        } else {
            int count = 0;
            int spaces = ~(col | pos | neg) & ((1 << n) - 1);
            while (spaces > 0) {
                int lowBit = spaces & (-spaces);
                count +=
                        dfsCountNQueue(
                                n, row + 1, col | lowBit, (pos | lowBit) >> 1, (neg | lowBit) << 1);
                spaces &= spaces - 1;
            }
            return count;
        }
    }

    public static int totalQueues(int n) {
        if (n <= 0) {
            return 0;
        }
        Set<Integer> col = new HashSet<>();
        Set<Integer> pos = new HashSet<>();
        Set<Integer> neg = new HashSet<>();
        return backTrack(n, 0, col, pos, neg);
    }

    public static int backTrack(
            int n, int row, Set<Integer> col, Set<Integer> pos, Set<Integer> neg) {
        if (n == row) {
            return 1;
        } else {
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (col.contains(i) || pos.contains(row + i) || neg.contains(row - i)) {
                    System.out.println("row=" + row + ",col=" + i);
                    continue;
                }
                col.add(i);
                pos.add(row + i);
                neg.add(row - i);
                System.out.println(
                        "row="
                                + row
                                + ",col="
                                + i
                                + ",col="
                                + col.toString()
                                + ",pos="
                                + pos.toString()
                                + ",neg="
                                + neg.toString());
                count += backTrack(n, row + 1, col, pos, neg);
                col.remove(i);
                pos.remove(row + i);
                neg.remove(row - i);
            }
            return count;
        }
    }
}
