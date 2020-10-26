package com.github.black.hole.base.algorithm.heap;

import lombok.Data;

/**
 * @author hairen.long
 * @date 2020/10/24
 */
@Data
public class MaxHeap {
    /**
     * 存储数据
     */
    private int[] data;
    /**
     * 堆可以存储最大个数
     */
    private int capacity;
    /**
     * 堆中已存储个数
     */
    private int size;

    public static void main(String[] args) {
        int[] arr = {12, 11, 8, 7, 9, 13};
        MaxHeap maxHeap = new MaxHeap(arr.length);
        for (int i = 0, len = arr.length; i < len; i++) {
            maxHeap.insert(arr[i]);
        }
        System.out.println(maxHeap);
        System.out.println("max=" + maxHeap.removeMax());
        System.out.println(maxHeap);
    }

    public MaxHeap(int capacity) {
        this.data = new int[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    public void insert(int value) {
        if (size == capacity) {
            return;
        }
        int index = size++;
        data[index] = value;
        for (; index > 0; index = parent(index)) {
            int parent = parent(index);
            if (data[index] > data[parent]) {
                swap(data, index, parent);
            } else {
                break;
            }
        }
    }

    public int removeMax() {
        if (size == 0) {
            return -1;
        }
        int max = data[0];
        data[0] = data[--size];
        heapily(0);
        return max;
    }

    private void heapily(int index) {
        while (true) {
            int maxPos = index;
            int left = left(index);
            int right = right(index);
            if (left < size && data[maxPos] < data[left]) {
                maxPos = left;
            }
            if (right < size && data[maxPos] < data[right]) {
                maxPos = right;
            }
            if (maxPos == index) {
                break;
            }
            swap(data, index, maxPos);
            index = maxPos;
        }
    }

    public int left(int i) {
        return (i + 1) * 2 - 1;
    }

    public int right(int i) {
        return (i + 1) * 2;
    }

    public int parent(int i) {
        // i为根结点
        if (i == 0) {
            return -1;
        }
        return (i - 1) / 2;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MaxHeap[capacity=" + this.capacity + ",size=" + this.size + ",data=[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]).append(",");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        sb.append("]]");
        return sb.toString();
    }
}
