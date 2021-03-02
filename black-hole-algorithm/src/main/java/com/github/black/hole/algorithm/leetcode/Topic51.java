package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hairen.long
 * @date 2021/2/18
 */
public class Topic51 {
    /**
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     *
     * <p>给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
     *
     * <p>每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     */
    public static void main(String[] args) {
        List<List<String>> result = solveNQueens(4);
        for (List<String> list : result) {
            System.out.println(list.toString());
        }
    }

    public static List<List<String>> solveNQueens(int n) {
        //TODO
        return null;
    }


}
