package com.github.black.hole.base.algorithm.practice.chapter6;

import lombok.Data;

import java.util.Objects;
import java.util.stream.IntStream;

/**
 * 基于单链表LRU算法, 单链表获取前驱比较麻烦，所有操作基本基于前驱节点操作
 *
 * @author hairen.long
 * @date 2020/11/11
 */
public class LruBaseLinkedList {

    private static final int DEFAULT_CAPACITY = 10;

    private int capacity;
    private int size;
    private Node head;

    public LruBaseLinkedList() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.head = new Node();
    }

    public LruBaseLinkedList(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.head = new Node();
    }

    public void add(int data) {
        Node preNode = findPreNode(data);
        if (Objects.nonNull(preNode)) {
            deleteNode(preNode);
            insertBegin(data);
        } else {
            if (size >= capacity) {
                deleteEnd();
            }
            insertBegin(data);
        }
    }

    private void deleteEnd() {
        Node preNode = head;
        if (preNode.next == null) {
            return;
        }
        while (preNode.next.next != null) {
            preNode = preNode.next;
        }
        preNode.next = null;
        size--;
    }

    private void insertBegin(int data) {
        Node node = new Node(data);
        node.next = head.next;
        head.next = node;
        size++;
    }

    private void deleteNode(Node preNode) {
        if (preNode != null) {
            Node temp = preNode.next;
            preNode.next = temp.next;
            temp = null;
            size--;
        }
    }

    private Node findPreNode(int data) {
        Node node = head;
        while (Objects.nonNull(node.next)) {
            if (Objects.equals(node.next.getValue(), data)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private void printAll() {
        Node node = head.next;
        while (node != null) {
            System.out.print(node.value + "\t");
            node = node.next;
        }
        System.out.println();
    }

    @Data
    private static class Node {
        private int value;
        private Node next;

        public Node() {}

        public Node(int data) {
            this.value = data;
        }
    }

    public static void main(String[] args) {
        LruBaseLinkedList list = new LruBaseLinkedList();
        IntStream.rangeClosed(1, 10).forEach(item -> list.add(item));
        list.printAll();
        list.add(4);
        list.printAll();
        list.add(3);
        list.printAll();
        list.add(13);
        list.printAll();
    }
}
