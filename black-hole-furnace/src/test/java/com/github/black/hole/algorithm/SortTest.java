package com.github.black.hole.algorithm;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2020/10/24
 */
public class SortTest {

    @Test
    public void quickTest() {
        int[] arr = {4, 2, 1, 6, 7, 3, 9, 8, 0};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("快速排序：" + Arrays.toString(arr));
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int index = getIndex(arr, low, high);
            quickSort(arr, low, index - 1);
            quickSort(arr, index + 1, high);
        }
    }

    private int getIndex(int[] arr, int low, int high) {
        int temp = arr[low];
        while (low < high) {
            while (low < high && temp < arr[high]) {
                high--;
            }
            arr[low] = arr[high];
            while (low < high && temp > arr[low]) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = temp;
        return low;
    }

    @Test
    public void mergeTest() {
        int[] arr = {4, 2, 1, 6, 7, 3, 9, 8, 0};
        mergeSort(arr, 0, arr.length - 1);
        System.out.println("归并排序：" + Arrays.toString(arr));
    }

    private int count = 0;

    private void mergeSort(int[] arr, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            System.out.println("第" + count++ + "次，mid=" + mid);
            mergeSort(arr, low, mid);
            mergeSort(arr, mid + 1, high);
            merge(arr, low, mid, high);
        }
    }

    private void merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int left = low;
        int right = mid + 1;
        int curr = 0;
        while (left <= mid && right <= high) {
            if (arr[left] < arr[right]) {
                temp[curr++] = arr[left++];
            } else {
                temp[curr++] = arr[right++];
            }
        }
        while (left <= mid) {
            temp[curr++] = arr[left++];
        }
        while (right <= high) {
            temp[curr++] = arr[right++];
        }
        for (int n = 0, len = temp.length; n < len; n++) {
            arr[low + n] = temp[n];
        }
    }

    @Test
    public void selectTest() {
        int[] arr = {4, 2, 1, 6, 7, 3, 9, 8, 0};
        selectSort(arr);
        System.out.println("选择排序：" + Arrays.toString(arr));
    }

    private void selectSort(int[] arr) {
        for (int i = 0, len = arr.length; i < len; i++) {
            int min = Integer.MAX_VALUE;
            int min_index = -1;
            for (int j = i; j < len; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    min_index = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[min_index];
            arr[min_index] = temp;
        }
    }

    @Test
    public void insertTest() {
        int[] arr = {4, 2, 1, 6, 7, 3, 9, 8, 0};
        insertSort(arr);
        System.out.println("插入排序：" + Arrays.toString(arr));
    }

    private void insertSort(int[] arr) {
        int curr, j;
        for (int i = 1, len = arr.length; i < len; i++) {
            curr = arr[i];
            for (j = i - 1; j >= 0; j--) {
                if (arr[j] > curr) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = curr;
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void bubbleSortTest() {
        int[] arr = {4, 2, 1, 6, 7, 3, 9, 8, 0};
        bubbleSort(arr);
        System.out.println("冒泡排序：" + Arrays.toString(arr));
    }

    private void bubbleSort(int[] arr) {
        for (int i = 0, len = arr.length; i < len; i++) {
            boolean isWap = false;
            for (int j = 0; j < len - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isWap = true;
                }
            }
            if (!isWap) {
                break;
            }
        }
    }
}
