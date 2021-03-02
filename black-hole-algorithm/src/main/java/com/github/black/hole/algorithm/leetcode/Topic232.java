package com.github.black.hole.algorithm.leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author hairen.long
 * @date 2021/2/12
 */
public class Topic232 {

    /** 请你仅使用两个栈实现先入先出队列。队列应当支持一般队列的支持的所有操作（push、pop、peek、empty） */
    public static void main(String[] args) {}

    private static class MyQueue {

        private Deque<Integer> inputStack;
        private Deque<Integer> outputStack;

        /** Initialize your data structure here. */
        public MyQueue() {
            this.inputStack = new LinkedList<>();
            this.outputStack = new LinkedList<>();
        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            this.inputStack.push(x);
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            if (this.outputStack.isEmpty()) {
                while (!this.inputStack.isEmpty()) {
                    this.outputStack.push(this.inputStack.pop());
                }
            }
            return this.outputStack.pop();
        }

        /** Get the front element. */
        public int peek() {
            if (this.outputStack.isEmpty()) {
                while (!this.inputStack.isEmpty()) {
                    this.outputStack.push(this.inputStack.pop());
                }
            }
            return this.outputStack.peek();
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return this.inputStack.isEmpty() && this.outputStack.isEmpty();
        }
    }
}
