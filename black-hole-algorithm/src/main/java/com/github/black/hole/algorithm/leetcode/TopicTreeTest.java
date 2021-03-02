package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

/**
 * @author hairen.long
 * @date 2021/2/19
 */
public class TopicTreeTest {

    public static void main(String[] args) {
        int[] nums = {6, 4, 5, 8, 7, 2, 3, 1, 9};
        TreeNode node = build(nums);

        System.out.println("前序遍历：");
        List<Integer> preOrder = new ArrayList<>();
        preOrder(node, preOrder);
        System.out.println(preOrder.toString());
        System.out.println(preOrder(node).toString());

        System.out.println("中序遍历：");
        List<Integer> inOrder = new ArrayList<>();
        inOrder(node, inOrder);
        System.out.println(inOrder.toString());
        System.out.println(inOrder(node).toString());

        System.out.println("后序遍历：");
        List<Integer> postOrder = new ArrayList<>();
        postOrder(node, postOrder);
        System.out.println(postOrder.toString());
        System.out.println(postOrder(node).toString());

        System.out.println("层次遍历：");
        System.out.println(levelOrder(node).toString());

        System.out.println("是否是二叉搜索树：" + isValidBST(node));
        System.out.println("是否是二叉搜索树：" + isValidBST1(node));
    }

    private static TreeNode prev;

    public static boolean isValidBST1(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (!isValidBST1(root.left)) {
            return false;
        }
        if (prev != null && prev.val > root.val) {
            return false;
        }
        prev = root;
        if (!isValidBST1(root.right)) {
            return false;
        }
        return true;
    }

    public static boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static boolean isValidBST(TreeNode node, int lower, int upper) {
        if (node == null) {
            return true;
        }
        if (lower >= node.val || upper <= node.val) {
            return false;
        }
        return isValidBST(node.left, lower, node.val) || isValidBST(node.right, node.val, upper);
    }

    public static List<List<Integer>> levelOrder(TreeNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> sub = new ArrayList<>();
            while (levelSize > 0) {
                node = queue.poll();
                sub.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                levelSize--;
            }
            result.add(sub);
        }
        return result;
    }

    public static List<Integer> postOrder(TreeNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(node);
        TreeNode curr = null, prev = null;
        while (!stack.isEmpty()) {
            curr = stack.peek();
            if ((curr.left == null && curr.right == null)
                    || (prev != null && (curr.left == prev || curr.right == prev))) {
                TreeNode temp = stack.pop();
                result.add(temp.val);
                prev = temp;
            } else {
                if (curr.right != null) {
                    stack.push(curr.right);
                }
                if (curr.left != null) {
                    stack.push(curr.left);
                }
            }
        }
        return result;
    }

    public static void postOrder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<Integer>();
        }
        postOrder(node.left, result);
        postOrder(node.right, result);
        result.add(node.val);
    }

    public static List<Integer> inOrder(TreeNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
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
        return result;
    }

    public static void inOrder(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        inOrder(root.left, result);
        result.add(root.val);
        inOrder(root.right, result);
    }

    public static List<Integer> preOrder(TreeNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
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
        return result;
    }

    public static void preOrder(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        result.add(root.val);
        preOrder(root.left, result);
        preOrder(root.right, result);
    }

    private static TreeNode build(int[] nums) {
        if (nums == null || nums.length < 1) {
            return null;
        }
        TreeNode root = new TreeNode(nums[0]);
        for (int i = 1, len = nums.length; i < len; i++) {
            insert(nums[i], root);
        }
        return root;
    }

    private static TreeNode insert(int value, TreeNode node) {
        if (node == null) {
            node = new TreeNode(value);
        } else if (value < node.val) {
            node.left = insert(value, node.left);
        } else {
            node.right = insert(value, node.right);
        }
        return node;
    }

    private static class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            TreeNode treeNode = (TreeNode) o;
            return val == treeNode.val;
        }

        @Override
        public String toString() {
            return "{" + "val=" + val + '}';
        }
    }
}
