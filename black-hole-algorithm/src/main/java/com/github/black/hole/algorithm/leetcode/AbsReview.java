package com.github.black.hole.algorithm.leetcode;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/8/25
 */
public class AbsReview {

    public static void main(String[] args) {
      System.out.println(556786*2);
    }

    public static class Aaa{
        private int id;

        public Aaa(int id) {
            this.id = id;
        }

        public int getId(){
            return id;
        }
    }

    public static void quickSort(int[] arr, int l, int h) {
        if (l < h) {
            int i = l, j = h, curr = arr[i];
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
            quickSort(arr, l, i - 1);
            quickSort(arr, i + 1, h);
        }
    }

    /**
     * 选择排序
     *
     * @param arr
     */
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

    /**
     * 希尔排序
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int len = arr.length, dk = len / 2;
        while (dk > 0) {
            for (int i = dk; i < len; i += dk) {
                int j = i - dk, curr = arr[i];
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
     * 插入排序
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 1, len = arr.length; i < len; i++) {
            int j = i - 1, curr = arr[i];
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
     * 冒泡排序
     *
     * @param arr
     */
    public static void bulletSort(int[] arr) {
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
