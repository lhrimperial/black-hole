package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/8/27
 */
public class SudoQueue {

    public static void main(String[] args) {
        testSudo();
        testNQueue();
    }

    public static void testNQueue() {
        solveNQueue(4)
                .forEach(
                        item -> {
                            item.forEach(System.out::println);
                            System.out.println();
                        });
        System.out.println("count n queue : " + countNQueue(4));
    }

    public static void testSudo() {
        char[][] chars = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        solveSudo(chars);
        printf(chars);

        char[][] result = {
            {'5', '3', '4', '6', '7', '8', '9', '1', '2'},
            {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
            {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
            {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
            {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
            {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
            {'9', '6', '1', '5', '3', '7', '2', '8', '4'},
            {'2', '8', '7', '4', '1', '9', '6', '3', '5'},
            {'3', '4', '5', '2', '8', '6', '1', '7', '9'}
        };

        char[][] chars2 = {
            {'8', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        System.out.println("char1 isValidSudoku : " + isValidSudo(chars));
        System.out.println("char2 isValidSudoku : " + isValidSudo(chars2));
    }

    public static boolean valid = false;

    public static void solveSudo(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        int[] row = new int[9], col = new int[9], block = new int[9];
        List<int[]> spaces = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '.') {
                    spaces.add(new int[] {i, j});
                } else {
                    int num = board[i][j] - '1';
                    row[i] |= 1 << num;
                    col[j] |= 1 << num;
                    block[i / 3 * 3 + j / 3] |= 1 << num;
                }
            }
        }
        dfsSolveSudo(board, 0, spaces, row, col, block);
    }

    public static void dfsSolveSudo(
            char[][] board, int pos, List<int[]> spaces, int[] row, int[] col, int[] block) {
        if (pos == spaces.size()) {
            valid = true;
            return;
        }
        int[] space = spaces.get(pos);
        int i = space[0], j = space[1], iBlock = i / 3 * 3 + j / 3;
        int bitSpaces = ~(row[i] | col[j] | block[iBlock]) & 0x1ff;
        while (bitSpaces > 0 && !valid) {
            int lowBit = bitSpaces & (-bitSpaces);
            int offset = Integer.bitCount(lowBit - 1);
            board[i][j] = (char) (offset + '1');
            row[i] |= 1 << offset;
            col[j] |= 1 << offset;
            block[iBlock] |= 1 << offset;
            dfsSolveSudo(board, pos + 1, spaces, row, col, block);
            row[i] ^= 1 << offset;
            col[j] ^= 1 << offset;
            block[iBlock] ^= 1 << offset;
            bitSpaces &= bitSpaces - 1;
        }
    }

    private static void printf(char[][] chars) {
        if (chars == null || chars.length == 0) {
            return;
        }
        for (int i = 0, rLen = chars.length; i < rLen; i++) {
            for (int j = 0, cLen = chars[i].length; j < cLen; j++) {
                System.out.print(chars[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean isValidSudo(char[][] board) {
        if (board == null || board.length == 0) {
            return false;
        }
        int[] row = new int[9], col = new int[9], block = new int[9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    int iBlock = i / 3 * 3 + j / 3;
                    if ((row[i] & (1 << num)) > 0
                            || (col[j] & (1 << num)) > 0
                            || (block[iBlock] & (1 << num)) > 0) {
                        return false;
                    }
                    row[i] |= 1 << num;
                    col[j] |= 1 << num;
                    block[iBlock] |= 1 << num;
                }
            }
        }
        return true;
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

    public static List<List<String>> solveNQueue(int n) {
        if (n <= 0) {
            return Collections.emptyList();
        }
        int col = 0, pos = 0, neg = 0;
        boolean[][] chess = new boolean[n][n];
        List<List<String>> result = new ArrayList<>();
        dfsSolveNQueue(n, 0, col, pos, neg, chess, result);
        return result;
    }

    public static void dfsSolveNQueue(
            int n, int row, int col, int pos, int neg, boolean[][] chess, List<List<String>> resp) {
        if (n == row) {
            List<String> sub = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(chess[i][j] ? "Q" : "*").append("\t");
                }
                sub.add(sb.toString());
            }
            resp.add(sub);
        } else {
            int spaces = ~(col | pos | neg) & ((1 << n) - 1);
            while (spaces > 0) {
                int lowBit = spaces & (-spaces);
                int bitCount = Integer.bitCount(lowBit - 1);
                chess[row][bitCount] = true;
                dfsSolveNQueue(
                        n,
                        row + 1,
                        col | lowBit,
                        (pos | lowBit) >> 1,
                        (neg | lowBit) << 1,
                        chess,
                        resp);
                chess[row][bitCount] = false;
                spaces &= spaces - 1;
            }
        }
    }
}
