package com.github.black.hole.base.algorithm.sort;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2020/10/11
 */
public class SortAlgorithm {

    /**
     * 如何分析一个排序算法？ 1. 排序算法的执行效率 - 最好情况、最坏情况、平均情况时间复杂度 - 时间复杂度的系数、常数 、低阶 - 比较次数和交换（或移动）次数 2.
     * 排序算法的内存消耗 - 原地排序（Sorted in place）。原地排序算法，就是特指空间复杂度是 O(1) 的排序算法 3. 排序算法的稳定性
     * 稳定性：这个概念是说，如果待排序的序列中存在值相等的元素，经过排序之后，相等元素之间原有的先后顺序不变。
     *
     * <p>基本排序概念： 有序度是数组中具有有序关系的元素对的个数 满有序度是全有序的数组 逆序度的定义正好跟有序度相反（默认从小到大为有序） 逆序度 = 满有序度 - 有序度
     */
    public static void main(String[] args) {
        int[] arr = {4, 2, 1, 6, 7, 3, 9, 8, 0};
        // bubbleSort(arr);
        // System.out.println("冒泡排序：" + Arrays.toString(arr));

        insertSort(arr);
        System.out.println("插入排序：" + Arrays.toString(arr));
    }

    /**
     * 插入排序
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
     * 冒泡排序：原地排序、稳定排序 最好情况时间复杂度O(n)、最坏情况时间复杂度O(n^2)、平均情况下的时间复杂度就是 O(n2)
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

    public static void heapSort(int[] arr) {


    }

    /**
     * 交换位置
     *
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
