package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/8/30
 */
public class Sorts {

    public static void main(String[] args) {
        int[] arr = {5, 3, 9, 6, 1, 7, 2, 4, 8};
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 归并排序 O(N*lgN)
     *
     * @param arr
     * @param low
     * @param high
     */
    public static void mergeSort(int[] arr, int low, int high) {
        if (arr == null || arr.length == 0 || low >= high) {
            return;
        }
        int mid = low + (high - low) / 2;
        mergeSort(arr, low, mid);
        mergeSort(arr, mid + 1, high);
        merge(arr, low, mid, high);
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
        for (int n = 0; n < tt.length; n++) {
            arr[low + n] = tt[n];
        }
        tt = null;
    }

    /**
     * 堆排序 O(N*lgN)
     *
     * @param arr
     */
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int len = arr.length - 1;
        // 堆化 从第一个非叶子节点开始
        for (int i = len / 2; i >= 0; i--) {
            heaping(arr, len, i);
        }
        // 排序，将堆顶移到最后，再堆化
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
            int t = arr[i];
            arr[i] = arr[pos];
            arr[pos] = t;
            i = pos;
        }
    }

    /**
     * 选择排序 O(n^2)
     *
     * @param arr
     */
    public static void selectSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 0, len = arr.length; i < len; i++) {
            int min = Integer.MAX_VALUE;
            int iMin = -1;
            for (int j = i; j < len; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    iMin = j;
                }
            }
            int t = arr[i];
            arr[i] = min;
            arr[iMin] = t;
        }
    }

    public static void shellSort(int[] arr) {
        int dk = arr.length / 2;
        while (dk > 0) {
            for (int i = dk; i < arr.length; i += dk) {
                int curr = arr[i], j = i - dk;
                for (; j >= 0; j -= dk) {
                    if (arr[j] > curr) {
                        arr[j + dk] = arr[j];
                    } else {
                        break;
                    }
                }
                arr[j + dk] = curr;
            }
            dk /= 2;
        }
    }

    /**
     * 插入排序O(n^2)
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 1, len = arr.length; i < len; i++) {
            int curr = arr[i], j = i - 1;
            for (; j >= 0; j--) {
                if (arr[j] > curr) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = curr;
        }
    }

    /**
     * 快速排序 N(nlogN)
     *
     * @param arr
     * @param l
     * @param h
     */
    public static void quickSort(int[] arr, int l, int h) {
        if (l < h) {
            int i = l, j = h, x = arr[i];
            while (i < j) {
                while (i < j && arr[j] >= x) {
                    j--;
                }
                arr[i] = arr[j];
                while (i < j && arr[i] <= x) {
                    i++;
                }
                arr[j] = arr[i];
            }
            arr[i] = x;
            quickSort(arr, l, i - 1);
            quickSort(arr, i + 1, h);
        }
    }

    /**
     * 冒泡排序O(n^2)
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int count = 0;
        for (int i = 0, len = arr.length; i < len; i++) {
            boolean isSwap = false;
            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int a = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = a;
                    isSwap = true;
                }
            }
            System.out.println(count++);
            if (!isSwap) {
                break;
            }
        }
    }
}
