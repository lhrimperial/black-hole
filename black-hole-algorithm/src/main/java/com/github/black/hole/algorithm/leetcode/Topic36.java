package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/8/23
 */
public class Topic36 {

    /**
     * 请你判断一个 9x9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
     *
     * <p>数字 1-9 在每一行只能出现一次。 数字 1-9 在每一列只能出现一次。 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
     * 数独部分空格内已填入了数字，空白格用 '.' 表示。
     */
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

        System.out.println("char1 isValidSudoku : " + isValidSudoku1(chars));
        System.out.println("char2 isValidSudoku : " + isValidSudoku1(chars2));
    }

    public static boolean isValidSudo1(char[][] board) {
        if (board == null || board.length == 0) {
            return false;
        }
        int[] row = new int[9], col = new int[9], block = new int[9];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    int blockIndex = i / 3 * 3 + j / 3;
                    int offset = 1 << num;
                    if ((row[i] & offset) > 0
                            || (col[j] & offset) > 0
                            || (block[blockIndex] & offset) > 0) {
                        return false;
                    } else {
                        row[i] |= offset;
                        col[j] |= offset;
                        block[blockIndex] |= offset;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isValidSudo(char[][] board) {
        if (board == null || board.length == 0) {
            return false;
        }
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][] block = new boolean[9][9];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    int blockIndex = i / 3 * 3 + j / 3;
                    if (row[i][num] || col[j][num] || block[blockIndex][num]) {
                        return false;
                    } else {
                        row[i][num] = col[j][num] = block[blockIndex][num] = true;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 位运算
     *
     * @param board
     * @return
     */
    public static boolean isValidSudoku1(char[][] board) {
        if (board == null || board.length <= 0) {
            return false;
        }
        int[] row = new int[9], col = new int[9], block = new int[9];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.') {
                    int pos = board[i][j];
                    if ((row[i] & (1 << pos)) > 0) {
                        return false;
                    }
                    row[i] |= 1 << pos;
                    if ((col[j] & (1 << pos)) > 0) {
                        return false;
                    }
                    col[j] |= 1 << pos;

                    int boxIndex = i / 3 * 3 + j / 3;
                    if ((block[boxIndex] & (1 << pos)) > 1) {
                        return false;
                    }
                    block[boxIndex] |= 1 << pos;
                }
            }
        }
        return true;
    }

    public static boolean isValidSudokuTest(char[][] board) {
        if (board == null || board.length == 0) {
            return false;
        }
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][][] block = new boolean[3][3][9];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.') {
                    int pos = board[i][j] - '1';
                    if (row[i][pos]) {
                        return false;
                    } else {
                        row[i][pos] = true;
                    }
                    if (col[pos][j]) {
                        return false;
                    } else {
                        col[pos][j] = true;
                    }
                    if (block[i / 3][j / 3][pos]) {
                        return false;
                    } else {
                        block[i / 3][j / 3][pos] = true;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isValidSudoku(char[][] board) {
        if (board == null || board.length == 0) {
            return false;
        }
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][] box = new boolean[9][9];
        for (int i = 0, len = board.length; i < len; i++) {
            for (int j = 0, inLen = board[i].length; j < inLen; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    int boxIndex = (i / 3) * 3 + j / 3;
                    if (row[i][num]) {
                        return false;
                    } else {
                        row[i][num] = true;
                    }
                    if (col[j][num]) {
                        return false;
                    } else {
                        col[j][num] = true;
                    }
                    if (box[boxIndex][num]) {
                        return false;
                    } else {
                        box[boxIndex][num] = true;
                    }
                }
            }
        }
        return true;
    }
}
