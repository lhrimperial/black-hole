package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/8/25
 */
public class AbsReview {

    public static void main(String[] args) {
        int[] arr = {1, 3, -1, -3, -4, 5, 3, 6, 7};
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void mergeSort(int[] arr, int low, int high) {
        if (arr == null || arr.length == 0 || low >= high) {
            return;
        }
        int mid = low + (high - low) / 2;
        mergeSort(arr, low, mid);
        mergeSort(arr, mid + 1, high);
        merge(arr, 0, mid, high);
    }

    public static void merge(int[] arr, int low, int mid, int high) {
        int[] tt = new int[high - low + 1];
        int i = low, j = mid + 1, k = 0;
        while (i <= mid && j <= high) {
            if (arr[i] < arr[j]) {
                tt[k++] = arr[i++];
            } else {
                tt[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            tt[k++] = arr[i++];
        }
        while (j <= high) {
            tt[k++] = arr[j++];
        }
        for (int n = 0, len = tt.length; n < len; n++) {
            arr[low + n] = tt[n];
        }
        tt = null;
    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int len = arr.length - 1;
        for (int i = len / 2; i >= 0; i--) {
            heaping(arr, len, i);
        }
        int k = len;
        while (k > 0) {
            int t = arr[0];
            arr[0] = arr[k];
            arr[k] = t;
            heaping(arr, --k, 0);
        }
    }

    public static void heaping(int[] arr, int n, int i) {
        while (true) {
            int pos = i, left = i * 2 + 1, right = i * 2 + 2;
            if (left <= n && arr[i] < arr[left]) {
                pos = left;
            }
            if (right <= n && arr[pos] < arr[right]) {
                pos = right;
            }
            if (pos == i) {
                break;
            }
            int t = arr[pos];
            arr[pos] = arr[i];
            arr[i] = t;
            i = pos;
        }
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (arr == null || arr.length == 0 || low >= high) {
            return;
        }
        int i = low, j = high, curr = arr[i];
        while (i < j) {
            while (i < j && arr[j] >= curr) {
                j--;
            }
            arr[i] = arr[j];
            while (i < j && arr[i] <= curr) {
                i++;
            }
            arr[j] = arr[i];
        }
        arr[i] = curr;
        quickSort(arr, low, i - 1);
        quickSort(arr, i + 1, high);
    }

    public static void selectSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 0, len = arr.length; i < len; i++) {
            int min = Integer.MAX_VALUE, iMin = -1;
            for (int j = i; j < len; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    iMin = j;
                }
            }
            arr[iMin] = arr[i];
            arr[i] = min;
        }
    }

    public static void shellSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int len = arr.length, dk = len / 2;
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
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 1, len = arr.length; i < len; i++) {
            int j = i - 1, curr = arr[i];
            for (; j >= 0 && arr[j] > curr; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = curr;
        }
    }

    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
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
