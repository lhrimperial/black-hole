package com.github.black.hole.algorithm.leetcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/9/5
 */
public class LruCache {

    private LruNode head;
    private LruNode tail;
    private final Map<String, LruNode> container;
    private int capacity;

    public LruCache(int capacity) {
        this.capacity = capacity;
        this.container = new HashMap<>();
    }

    public void put(String key, Object value) {
        LruNode node = container.get(key);
        if (node != null) {
            node.value = value;
            remove(node, false);
        } else {
            node = new LruNode(key, value);
            if (container.size() >= capacity) {
                remove(tail, true);
            }
            container.put(key, node);
        }
        setHead(node);
    }

    public Object get(String key) {
        LruNode node = container.get(key);
        if (node != null) {
            remove(node, false);
            setHead(node);
            return node.value;
        }
        return null;
    }

    private void setHead(LruNode node) {
        if (head != null) {
            node.next = head;
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
    }

    private void remove(LruNode node, boolean real) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        node.prev = null;
        node.next = null;
        if (real) {
            container.remove(node.key);
        }
    }

    public static class LruNode {
        private String key;
        private Object value;
        private LruNode prev;
        private LruNode next;

        public LruNode(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
