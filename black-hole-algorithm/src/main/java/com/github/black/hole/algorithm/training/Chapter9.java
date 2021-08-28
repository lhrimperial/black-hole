package com.github.black.hole.algorithm.training;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author hairen.long
 * @date 2021/8/21
 */
public class Chapter9 {
    /** 232\255 通过队列和堆栈实现彼此 */

    public static void main(String[] args) {
       MyStack stack = new MyStack();
       stack.push(1);
       stack.push(2);
       stack.push(3);
       System.out.println(stack.pop());
       System.out.println(stack.pop());
    }

    private static class MyStack {
        private Queue<Integer> queue1;
        private Queue<Integer> queue2;

        public MyStack() {
            queue1 = new LinkedList<>();
            queue2 = new LinkedList<>();
        }

        public void push(int val) {
            queue2.offer(val);
            while (!queue1.isEmpty()) {
                queue2.offer(queue1.poll());
            }
            Queue<Integer> temp = queue1;
            queue1 = queue2;
            queue2 = temp;
        }

        public int pop(){
            return queue1.poll();
        }
    }

    private static class MyQueue {

        private Deque<Integer> stack1;
        private Deque<Integer> stack2;

        public MyQueue(){
            stack1 = new ArrayDeque<>();
            stack2 = new ArrayDeque<>();
        }

        public void push(int val) {
            this.stack1.push(val);
        }

        public int pop() {
            if (stack2.isEmpty()) {
                while (stack1.peek() != null) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.pop();
        }

        public int peek(){
            if (stack2.isEmpty()) {
                while (stack1.peek() != null) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.peek();
        }
    }
}
