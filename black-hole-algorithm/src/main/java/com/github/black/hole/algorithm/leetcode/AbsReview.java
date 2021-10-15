package com.github.black.hole.algorithm.leetcode;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/8/25
 */
public class AbsReview {

    public static void main(String[] args) {
        ConcurrentHashMap<Integer,Integer> map = new ConcurrentHashMap<>();
        map.put(1, 1);
    }

    public static int find(int[] nums, int tagget) {
        // 省略校验
        int left = 0, right = 1, s = 0, ans = Integer.MAX_VALUE;
        while (left < nums.length) {
            s += nums[right++];
            while (s >= tagget) {
                ans = Math.min(ans, right - left);
                s -= nums[left++];
            }
        }
        return ans;
    }

    public static class LruCache {
        private int capacity;
        private Node head;
        private Node tail;
        private Map<Integer, Node> container;

        public LruCache(int capacity) {
            this.capacity = capacity;
            this.container = new HashMap<>(capacity);
        }

        public Integer get(int key) {
            Node node = container.get(key);
            if (node != null) {
                remove(node, false);
                setHead(node);
                return node.value;
            }
            return null;
        }

        public void put(int key, int value) {
            Node node = container.get(key);
            if (node == null) {
                node = new Node(key, value);
                if (container.size() >= capacity) {
                    remove(tail, true);
                }
                container.put(key, node);
            } else {
                node.value = value;
                remove(node, false);
            }
            setHead(node);
        }

        private void setHead(Node node) {
            if (head != null) {
                node.next = head;
                head.prev = node;
            }
            head = node;
            if (tail == null) {
                tail = node;
            }
        }

        private void remove(Node node, boolean real) {
            if (node.prev != null) {
                node.prev.next = node.next;
            } else {
                head = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                tail = node.prev;
            }
            node.next = null;
            node.prev = null;
            if (real) {
                container.remove(node.key);
            }
        }
    }

    public static class Node {
        public int key;
        public int value;
        public Node prev;
        public Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void test() {
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
        SudoQueue.printf(chars);

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

    public static void solveSudo(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        int len = board.length;
        int[] row = new int[len], col = new int[len], block = new int[len];
        List<int[]> spaces = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (board[i][j] == '.') {
                    spaces.add(new int[] {i, j});
                } else {
                    int num = board[i][j] - '1', offset = 1 << num;
                    row[i] |= offset;
                    col[j] |= offset;
                    block[i / 3 * 3 + j / 3] |= offset;
                }
            }
        }
        dfsSolveSudo(board, 0, spaces, row, col, block);
    }

    public static boolean valid = false;

    public static void dfsSolveSudo(
            char[][] board, int pos, List<int[]> spaces, int[] row, int[] col, int[] block) {
        if (pos == spaces.size()) {
            valid = true;
            return;
        }
        int[] space = spaces.get(pos);
        int i = space[0], j = space[1], ib = i / 3 * 3 + j / 3;
        int bitSpaces = ~(row[i] | col[j] | block[ib]) & 0x1ff;
        while (bitSpaces > 0 && !valid) {
            int lowBit = bitSpaces & -bitSpaces;
            int bitCount = Integer.bitCount(lowBit - 1);
            board[i][j] = (char) (bitCount + '1');
            int offset = 1 << bitCount;
            row[i] |= offset;
            col[j] |= offset;
            block[ib] |= offset;
            dfsSolveSudo(board, pos + 1, spaces, row, col, block);
            row[i] ^= offset;
            col[j] ^= offset;
            block[ib] ^= offset;
            bitSpaces &= bitSpaces - 1;
        }
    }

    public static boolean isValidSudo(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }
        int len = board.length;
        int[] row = new int[len], col = new int[len], block = new int[len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1', offset = 1 << num;
                    int iBlock = i / 3 * 3 + j / 3;
                    if ((row[i] & offset) > 0
                            || (col[j] & offset) > 0
                            || (block[iBlock] & offset) > 0) {
                        return false;
                    }
                    row[i] |= offset;
                    col[j] |= offset;
                    block[iBlock] |= offset;
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
                int lowBit = spaces & -spaces;
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
        boolean[][] chess = new boolean[n][n];
        List<List<String>> res = new ArrayList<>();
        dfsSolveNQueue(n, 0, 0, 0, 0, chess, res);
        return res;
    }

    public static void dfsSolveNQueue(
            int n, int row, int col, int pos, int neg, boolean[][] chess, List<List<String>> res) {
        if (n == row) {
            List<String> sub = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(chess[i][j] ? "Q" : ".");
                }
                sub.add(sb.toString());
            }
            res.add(sub);
        } else {
            int spaces = ~(col | pos | neg) & ((1 << n) - 1);
            while (spaces > 0) {
                int lowBit = spaces & -spaces;
                int bitCount = Integer.bitCount(lowBit - 1);
                chess[row][bitCount] = true;
                dfsSolveNQueue(
                        n,
                        row + 1,
                        col | lowBit,
                        (pos | lowBit) >> 1,
                        (neg | lowBit) << 1,
                        chess,
                        res);
                chess[row][bitCount] = false;
                spaces &= spaces - 1;
            }
        }
    }

    public static void heapSort(int[] arr) {
        int len = arr.length - 1;
        for (int i = len / 2; i >= 0; i--) {
            heaping(arr, len, i);
        }
        int k = len;
        while (k > 0) {
            int t = arr[k];
            arr[k] = arr[0];
            arr[0] = t;
            heaping(arr, --k, 0);
        }
    }

    public static void heaping(int[] arr, int n, int i) {
        while (true) {
            int p = i, left = i * 2 + 1, right = i * 2 + 2;
            if (left <= n && arr[i] < arr[left]) {
                p = left;
            }
            if (right <= n && arr[p] < arr[right]) {
                p = right;
            }
            if (p == i) {
                break;
            }
            int t = arr[p];
            arr[p] = arr[i];
            arr[i] = t;
            i = p;
        }
    }

    public static void mergeSort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int mid = low + (high - low) / 2;
        mergeSort(arr, low, mid);
        mergeSort(arr, mid + 1, high);
        merge(arr, low, mid, high);
    }

    public static void merge(int[] arr, int low, int mid, int high) {
        int[] tt = new int[high - low + 1];
        int l = low, r = mid + 1, k = 0;
        while (l <= mid && r <= high) {
            if (arr[l] <= arr[r]) {
                tt[k++] = arr[l++];
            } else {
                tt[k++] = arr[r++];
            }
        }
        while (l <= mid) {
            tt[k++] = arr[l++];
        }
        while (r <= high) {
            tt[k++] = arr[r++];
        }
        for (int i = 0; i < tt.length; i++) {
            arr[low + i] = tt[i];
        }
        tt = null;
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int l = low, r = high, curr = arr[low];
            while (l < r) {
                while (l < r && arr[r] >= curr) {
                    r--;
                }
                arr[l] = arr[r];
                while (l < r && arr[l] <= curr) {
                    l++;
                }
                arr[r] = arr[l];
            }
            arr[l] = curr;
            quickSort(arr, low, l - 1);
            quickSort(arr, l + 1, high);
        }
    }

    public static void shellSort(int[] arr) {
        int len = arr.length, dk = len >> 1;
        while (dk > 0) {
            for (int i = dk; i < len; i += dk) {
                int j = i - dk, curr = arr[i];
                for (; j >= 0 && arr[j] > curr; j -= dk) {
                    arr[j + dk] = arr[j];
                }
                arr[j + dk] = curr;
            }
            dk /= 2;
        }
    }

    public static void insertSort(int[] arr) {
        for (int i = 1, len = arr.length; i < len; i++) {
            int j = i - 1, curr = arr[i];
            for (; j >= 0 && arr[j] > curr; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = curr;
        }
    }

    public static void bulletSort(int[] arr) {
        for (int i = 0, len = arr.length; i < len; i++) {
            boolean swap = false;
            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                    swap = true;
                }
            }
            if (!swap) {
                break;
            }
        }
    }
}
