package com.github.black.hole.base.algorithm.leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author hairen.long
 * @date 2020/11/11
 */
public class Solution155 {
    /**
     * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
     *
     * <p>push(x) —— 将元素 x 推入栈中。
     *
     * <p>pop() —— 删除栈顶的元素。
     *
     * <p>top() —— 获取栈顶元素。
     *
     * <p>getMin() —— 检索栈中的最小元素。
     */
    private static class MinStack {

        private Deque<Integer> stack;
        private Deque<Integer> minStack;

        public MinStack() {
            stack = new LinkedList<>();
            minStack = new LinkedList<>();
            minStack.push(Integer.MAX_VALUE);
        }

        public void push(int x) {
            stack.push(x);
            minStack.push(Math.min(minStack.peek(), x));
        }

        public void pop() {
            stack.pop();
            minStack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }
}
