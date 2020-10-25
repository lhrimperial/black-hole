package com.github.black.hole.base.algorithm.heap;

import lombok.Data;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2020/10/24
 */
@Data
public class Heap {
    /** 存储数据 */
    private int[] data;
    /** 堆可以存储最大个数 */
    private int capacity;
    /** 堆中已存储个数 */
    private int size;

    public static void main(String[] args) {
        int[] arr = {8, 5, 9, 4, 2, 3, 6, 7, 1};
        Heap heap = new Heap(arr.length);
        for (int i = 0, len = arr.length; i < len; i++) {
            heap.insert(arr[i]);
        }
        System.out.println(Arrays.toString(heap.data));
        System.out.println(heap.removeMax());
        System.out.println(Arrays.toString(heap.data));
    }

    public Heap(int capacity) {
        this.data = new int[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    public void insert(int value) {
        if (size >= capacity) {
            return;
        }
        this.data[size] = value;
        shiftUp(size);
        System.out.println(Arrays.toString(this.data));
        size++;
    }

    public int removeMax() {
        if (size == 0) {
            return -1;
        }
        int t = data[0];
        // 将最后一个元素放到第一个元素位置
        data[1] = data[size];
        // 然后将第一个元素下移到适当位置
        shiftDown(1);
        size--;
        return t;
    }

    private void shiftDown(int i) {
        while ((2 * i + 1) <= size) {
            int j = i * 2 + 1;
            // 让j指向他的孩子结点中的大的那一个
            if (j + 1 <= size && data[j] < data[j + 1]) {
                j = j + 1;
            }
            if (data[i] > data[j]) {
                break;
            }

            // 元素下移
            swap(this.data, i, j);
            i = j;
        }
    }

    private void shiftUp(int index) {
        while (index > 0 && data[index] > data[(index - 1) / 2]) {
            swap(data, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private int parent(int index) {
        if (index == 0) {
            throw new RuntimeException("跟节点没有父节点");
        }
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
