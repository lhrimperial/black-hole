package com.github.black.hole.base.algorithm.sort;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2020/10/11
 */
public class SortAlgorithm {

    /**
     * 如何分析一个排序算法？
     * 1. 排序算法的执行效率
     * - 最好情况、最坏情况、平均情况时间复杂度
     * - 时间复杂度的系数、常数 、低阶
     * - 比较次数和交换（或移动）次数
     * 2. 排序算法的内存消耗
     * - 原地排序（Sorted in place）。原地排序算法，就是特指空间复杂度是 O(1) 的排序算法
     * 3. 排序算法的稳定性
     * 稳定性：这个概念是说，如果待排序的序列中存在值相等的元素，经过排序之后，相等元素之间原有的先后顺序不变。
     * <p>
     * 基本排序概念：
     * 有序度是数组中具有有序关系的元素对的个数
     * 满有序度是全有序的数组
     * 逆序度的定义正好跟有序度相反（默认从小到大为有序）
     * 逆序度 = 满有序度 - 有序度
     */

    public static void main(String[] args) {
        int[] arr = {4, 2, 1, 6, 7, 3, 9, 8, 0};
        // bubbleSort(arr);
        // System.out.println("冒泡排序：" + Arrays.toString(arr));

        // insertSort(arr);
        // System.out.println("插入排序：" + Arrays.toString(arr));

        // bubbleSort2(arr,arr.length);
        // System.out.println("选择排序：" + Arrays.toString(arr));

        mergeSort(arr, 0, arr.length-1);
        System.out.println("归并排序：" + Arrays.toString(arr));
    }

    public static void mergeSort(int[] arr, int low, int high){
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(arr, 0, mid);
            mergeSort(arr, mid + 1, high);
            merge(arr, low, mid, high);
        }
    }

    public static void merge(int[] arr, int low, int mid, int high){
        int[] temp = new int[high - low + 1];
        int left = low;
        int right = mid + 1;
        int curr = 0;
        while (left <= mid && right <= high) {
            if (arr[left] < arr[right]) {
                temp[curr++] = arr[left++];
            }else {
                temp[curr++] = arr[right++];
            }
        }
        // 把左边剩余的数移入数组
        while (left <= mid){
            temp[curr++] = arr[left++];
        }
        // 把右边边剩余的数移入数组
        while (right <= high){
            temp[curr++] = arr[right++];
        }
        for (int n = 0, len = temp.length; n < len; n++) {
            arr[n] = temp[n];
        }

    }

    /**
     * 选择排序，每次从未排序数组中选择最小的排在前面
     * 原地排序、不稳定排序
     * 最好情况时间复杂度、最坏情况和平均情况时间复杂度都为 O(n2)
     *
     * @param arr
     */
    public static void selectSort(int[] arr) {
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
            arr[i] = min;
            arr[min_index] = temp;
        }
    }

    /**
     * 插入排序原地排序、稳定排序
     * 最好情况时间复杂度O(n)、最坏情况时间复杂度O(n^2)、平均情况下的时间复杂度就是 O(n2)
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        int curr, j;
        for (int i = 1, len = arr.length; i < len; i++) {
            curr = arr[i];
            j = i - 1;
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
     * 冒泡排序：原地排序、稳定排序
     * 最好情况时间复杂度O(n)、最坏情况时间复杂度O(n^2)、平均情况下的时间复杂度就是 O(n2)
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        for (int i = 0, len = arr.length; i < len; i++) {
            boolean flag = false;
            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    /**
     * 冒泡排序改进:在每一轮排序后记录最后一次元素交换的位置,作为下次比较的边界,
     * 对于边界外的元素在下次循环中无需比较.
     */
    public static void bubbleSort2(int[] a, int n) {
        if (n <= 1) {
            return;
        } ;

        // 最后一次交换的位置
        int lastExchange = 0;
        // 无序数据的边界,每次只需要比较到这里即可退出
        int sortBorder = n - 1;
        for (int i = 0; i < n; i++) {
            // 提前退出标志位
            boolean flag = false;
            for (int j = 0; j < sortBorder; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    // 此次冒泡有数据交换
                    flag = true;
                    // 更新最后一次交换的位置
                    lastExchange = j;
                }
            }
            sortBorder = lastExchange;
            // 没有数据交换，提前退出
            if (!flag) {
                break;
            }
        }
    }

}
