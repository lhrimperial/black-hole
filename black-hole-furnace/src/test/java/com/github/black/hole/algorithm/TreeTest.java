package com.github.black.hole.algorithm;

import com.github.black.hole.base.algorithm.tree.TreeNode;
import org.junit.Test;

import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/10/24
 */
public class TreeTest {

    private TreeNode root;

    @Test
    public void builderTree() {
        int[] arr = {8, 5, 2, 1, 3, 4, 6, 7, 9};
        for (int i = 0, len = arr.length; i < len; i++) {
            insert(arr[i]);
        }
        System.out.println("前序遍历：");
        perOrder(root);
        System.out.println("\n中序遍历：");
        inOrder(root);
        System.out.println("\n后序遍历：");
        postOrder(root);
    }

    private void insert(int data) {
        root = insert(root, data);
    }

    private void postOrder(TreeNode node) {
        if (Objects.nonNull(node)) {
            postOrder(node.getLeft());
            postOrder(node.getRight());
            System.out.print(node.getVal() + "\t");
        }
    }

    private void inOrder(TreeNode node) {
        if (Objects.nonNull(node)) {
            inOrder(node.getLeft());
            System.out.print(node.getVal() + "\t");
            inOrder(node.getRight());
        }
    }

    private void perOrder(TreeNode node) {
        if (Objects.nonNull(node)) {
            System.out.print(node.getVal() + "\t");
            perOrder(node.getLeft());
            perOrder(node.getRight());
        }
    }

    private TreeNode insert(TreeNode node, int data) {
        if (Objects.isNull(node)) {
            node = new TreeNode(data);
        } else if (data < node.getVal()) {
            node.setLeft(insert(node.getLeft(), data));
        } else {
            node.setRight(insert(node.getRight(), data));
        }
        return node;
    }
}
