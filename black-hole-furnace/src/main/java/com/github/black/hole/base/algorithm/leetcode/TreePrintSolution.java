package com.github.black.hole.base.algorithm.leetcode;

import com.alibaba.fastjson.JSON;
import com.github.black.hole.base.algorithm.tree.BinaryTree;
import com.github.black.hole.base.algorithm.tree.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author hairen.long
 * @date 2020/10/17
 */
public class TreePrintSolution {

    /**
     * 从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = {11, 8, 3, 5, 4, 12, 14, 7, 2, 13, 21};
        BinaryTree tree = new BinaryTree();
        for (int i = 0, len = arr.length; i < len; i++) {
            tree.insert(arr[i]);
        }
        List<List<Integer>> list = printNodes1(tree.getRootNode());
        list.forEach(item -> System.out.println(JSON.toJSONString(item)));
    }

    public static List<List<Integer>> printNodes1(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>(size * 2);

            for (int i = 0; i < size; i++) {
                TreeNode next = queue.poll();
                level.add(next.getVal());
                if (next.getLeft() != null) {
                    queue.add(next.getLeft());
                }
                if (next.getRight() != null) {
                    queue.add(next.getRight());
                }
            }
            result.add(level);
        }

        return result;
    }

    private static List<List<Integer>> printNodes(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> ans = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addFirst(root);
        while (!queue.isEmpty()) {
            List<Integer> curList = new ArrayList<>();
            int curLevelSize = queue.size();
            while (curLevelSize > 0) {
                TreeNode node = queue.removeLast();
                if (node.getLeft() != null) {
                    queue.addFirst(node.getLeft());
                }
                if (node.getRight() != null) {
                    queue.addFirst(node.getRight());
                }
                curList.add(node.getVal());
                curLevelSize--;
            }
            ans.add(curList);
        }
        return ans;
    }
}
