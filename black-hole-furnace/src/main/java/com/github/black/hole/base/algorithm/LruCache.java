package com.github.black.hole.base.algorithm;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/10/16
 */
@Data
public class LruCache {

    private Map<String, LruNode> container;
    private int capacity;
    private LruNode head;
    private LruNode tail;

    public LruCache(int capacity) {
        this.capacity = capacity;
        this.container = new HashMap<>(capacity);
    }

    public void set(String key, Object value) {
        LruNode node = container.get(key);
        if (Objects.nonNull(node)) {
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
        if (Objects.nonNull(node)) {
            remove(node, false);
            setHead(node);
            return node.value;
        }
        return null;
    }

    private void setHead(LruNode node) {
        if (Objects.nonNull(head)) {
            node.next = head;
            head.prev = node;
        }
        head = node;
        if (Objects.isNull(tail)) {
            tail = node;
        }
    }

    private void remove(LruNode node, boolean flag) {
        if (Objects.nonNull(node.prev)) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (Objects.nonNull(node.next)) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        node.prev = null;
        node.next = null;
        if (flag) {
            container.remove(node.key);
        }
    }

    @Data
    static class LruNode {
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
