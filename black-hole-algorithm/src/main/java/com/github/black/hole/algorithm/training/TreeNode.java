package com.github.black.hole.algorithm.training;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author hairen.long
 * @date 2021/8/21
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TreeNode) {
            return this.val == ((TreeNode) obj).val;
        }
        return false;
    }

    public static TreeNode buildTree(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(arr[0]);
        for (int i = 1, len = arr.length; i < len; i++) {
            insert(arr[i], root);
        }
        return root;
    }

    public static TreeNode insert(int val, TreeNode parent) {
        if (parent == null) {
            parent = new TreeNode(val);
        } else if (val < parent.val) {
            parent.left = insert(val, parent.left);
        } else {
            parent.right = insert(val, parent.right);
        }
        return parent;
    }

    public static void preOrder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        result.add(node.val);
        preOrder(node.left, result);
        preOrder(node.right, result);
    }

    public static void preOrderNoRecursive(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                result.add(node.val);
                stack.push(node);
                node = node.left;
            }
            if (!stack.isEmpty()) {
                node = stack.pop().right;
            }
        }
    }

    public static void inOrder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        inOrder(node.left, result);
        result.add(node.val);
        inOrder(node.right, result);
    }

    public static void inOrderNoRecursive(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            if (!stack.isEmpty()) {
                node = stack.pop();
                result.add(node.val);
                node = node.right;
            }
        }
    }

    public static void postOrder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        postOrder(node.left, result);
        postOrder(node.right, result);
        result.add(node.val);
    }

    public static void postOrderNoRecursive(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(node);
        TreeNode curr = null, prev = null;
        while (!stack.isEmpty()) {
            curr = stack.peek();
            if ((curr.left == null && curr.right == null)
                    || (prev != null && (prev == curr.left || prev == curr.right))) {
                node = stack.pop();
                result.add(node.val);
                prev = node;
            } else {
                if (curr.right != null) {
                    stack.push(curr.right);
                }
                if (curr.left != null) {
                    stack.push(curr.left);
                }
            }
        }
    }

    public static void leveOrder(TreeNode node, List<List<Integer>> result) {
        if (node == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> subResult = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                node = queue.poll();
                subResult.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(subResult);
        }
    }
}
