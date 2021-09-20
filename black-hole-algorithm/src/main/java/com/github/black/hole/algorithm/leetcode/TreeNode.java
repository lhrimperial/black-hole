package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

/**
 * @author hairen.long
 * @date 2021/2/15
 */
public class TreeNode {

    public int value;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TreeNode){
            return Objects.equals(this.value, ((TreeNode) obj).value);
        }
        return false;
    }

    public static TreeNode insert(int value, TreeNode parent) {
        if (parent == null) {
            parent = new TreeNode(value);
        } else if (value < parent.value) {
            parent.left = insert(value, parent.left);
        } else {
            parent.right = insert(value, parent.right);
        }
        return parent;
    }

    public static TreeNode buildTree(int[] arr) {
        if (arr == null || arr.length < 1) {
            return null;
        }
        TreeNode root = new TreeNode(arr[0]);
        for (int i = 1, len = arr.length; i < len; i++) {
            insert(arr[i], root);
        }
        return root;
    }

    public static void preOrder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        result.add(node.value);
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
                result.add(node.getValue());
                stack.push(node);
                node = node.getLeft();
            }
            if (!stack.isEmpty()) {
                node = stack.pop().getRight();
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
        result.add(node.value);
        inOrder(node.right, result);
    }

    public static void inOrderNonRecursive(TreeNode node, List<Integer> result) {
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
                node = node.getLeft();
            }
            if (!stack.isEmpty()) {
                TreeNode temp = stack.pop();
                result.add(temp.getValue());
                node = temp.getRight();
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
        result.add(node.value);
    }

    public static void postOrderNonRecursive(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = null, prev = null;
        stack.push(node);
        while (!stack.isEmpty()) {
            curr = stack.peek();
            // 当前节点的左右孩子都为空 或者 前驱节点为当前节点的左孩子或者右孩子
            if ((curr.getLeft() == null && curr.getRight() == null)
                    || (prev != null
                            && (Objects.equals(prev, curr.getLeft())
                                    || Objects.equals(prev, curr.getRight())))) {
                TreeNode temp = stack.pop();
                result.add(temp.getValue());
                prev = temp;
            } else {
                if (curr.getRight() != null) {
                    stack.push(curr.getRight());
                }
                if (curr.getLeft() != null) {
                    stack.push(curr.getLeft());
                }
            }
        }
    }

    public static void levelOrder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        TreeNode curr = null;
        while (!queue.isEmpty()) {
            curr = queue.poll();
            result.add(curr.getValue());
            if (curr.getLeft() != null) {
                queue.offer(curr.getLeft());
            }
            if (curr.getRight() != null) {
                queue.offer(curr.getRight());
            }
        }
    }
}
