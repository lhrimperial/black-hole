package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/8/23
 */
public class Topic37 {

    /**
     * 编写一个程序，通过填充空格来解决数独问题。
     *
     * <p>数独的解法需 遵循如下规则：
     *
     * <p>数字 1-9 在每一行只能出现一次。 数字 1-9 在每一列只能出现一次。 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
     * 数独部分空格内已填入了数字，空白格用 '.' 表示。
     */
    private static boolean valid;

    public static void main(String[] args) {
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
        solveSudoku2(chars);
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
    }

    public static void solveSudoku2(char[][] board) {
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
                    int offset = 1 << num;
                    row[i] |= offset;
                    col[j] |= offset;
                    block[i / 3 * 3 + j / 3] |= offset;
                }
            }
        }
        dfsSolveSudoku1(board, 0, spaces, row, col, block);
    }

    public static void dfsSolveSudoku1(
            char[][] board, int pos, List<int[]> spaces, int[] row, int[] col, int[] block) {
        if (pos == spaces.size()) {
            valid = true;
            return;
        }
        int[] space = spaces.get(pos);
        int i = space[0], j = space[1];
        int iBlock = i / 3 * 3 + j / 3;
        // 取出 这个位置关联的行 列 块 可填的数字，这里是取出已用的再取反就是可用的
        int bitSpace = ~(row[i] | col[j] | block[iBlock]) & 0x1ff;
        while (bitSpace != 0 && !valid) {
            // 取低位的数字, 1代表这个位上有数字，位置下标即为这个数值 eg：101000，最低位1表示3
            int digitBit = bitSpace & (-bitSpace);
            // 求一个位所在位置，需要通过 -1 的方式统计他后面有多少个1即求出他的位置
            int digit = Integer.bitCount(digitBit - 1);
            board[i][j] = (char) (digit + '1');
            int offset = 1 << digit;
            row[i] |= offset;
            col[j] |= offset;
            block[iBlock] |= offset;
            dfsSolveSudoku1(board, pos + 1, spaces, row, col, block);
            row[i] ^= offset;
            col[j] ^= offset;
            block[iBlock] ^= offset;
            // 清除低位
            bitSpace &= bitSpace - 1;
        }
    }

    public static void solveSudoku1(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][][] block = new boolean[3][3][9];
        List<int[]> spaces = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    spaces.add(new int[] {i, j});
                } else {
                    int num = board[i][j] - '1';
                    row[i][num] = col[j][num] = block[i / 3][j / 3][num] = true;
                }
            }
        }
        dfsSolveSudoku1(board, 0, row, col, block, spaces);
    }

    private static void dfsSolveSudoku1(
            char[][] board,
            int pos,
            boolean[][] row,
            boolean[][] col,
            boolean[][][] block,
            List<int[]> spaces) {
        if (pos == spaces.size()) {
            valid = true;
            return;
        }
        int[] space = spaces.get(pos);
        int i = space[0], j = space[1];
        for (int num = 0; num < 9 && !valid; num++) {
            if (!row[i][num] && !col[j][num] && !block[i / 3][j / 3][num]) {
                row[i][num] = col[j][num] = block[i / 3][j / 3][num] = true;
                board[i][j] = (char) (num + '1');
                dfsSolveSudoku1(board, pos + 1, row, col, block, spaces);
                row[i][num] = col[j][num] = block[i / 3][j / 3][num] = false;
            }
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
}
