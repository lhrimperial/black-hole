package com.github.black.hole.base.algorithm.practice.chapter8;

import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2020/11/11
 */
public class ArrayStack {
    private Object[] item;
    private int capacity;
    private int size;

    public ArrayStack(int capacity) {
        this.item = new Object[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    public boolean push(Object element) {
        if (size == capacity) {
            return false;
        }
        item[size++] = element;
        return true;
    }

    public Object pop() {
        if (size == 0) {
            return null;
        }
        return item[--size];
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(5);
        StackBasedOnLinkedList linkedStack = new StackBasedOnLinkedList();
        IntStream.rangeClosed(1, 5)
                .forEach(
                        item -> {
                            linkedStack.push(item);
                            stack.push(item);
                        });
        for (int i = 0, len = stack.size; i < len; i++) {
            System.out.println("array:" + stack.pop());
            System.out.println("linked:" + linkedStack.pop());
        }
    }
}
