package com.github.black.hole.base.algorithm.practice.chapter8;

/**
 * @author hairen.long
 * @date 2020/11/11
 */
public class StackBasedOnLinkedList {

    private Node top;

    public void push(int value) {
        Node node = new Node(value);
        if (top == null) {
            top = node;
        } else {
            node.next = top;
            top = node;
        }
    }

    public int pop() {
        if (top == null) {
            return -1;
        }
        int value = top.data;
        top = top.next;
        return value;
    }

    private static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }
    }
}
