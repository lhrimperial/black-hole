package com.github.black.hole.base.algorithm.tree;

import lombok.Data;

import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/10/16
 */
@Data
public class BinaryTree {

    private TreeNode rootNode;

    public void insert(int val) {
        rootNode = insert(rootNode, val);
    }

    private TreeNode insert(TreeNode node, int data) {
        if (Objects.isNull(node)) {
            node = new TreeNode(data);
        } else if (node.getVal() > data) {
            node.setLeft(insert(node.getLeft(), data));
        } else {
            node.setRight(insert(node.getRight(), data));
        }
        return node;
    }

    /**
     * 前序遍历：父左右
     *
     * @param node
     */
    public void preOrderTraverse(TreeNode node) {
        if (Objects.nonNull(node)) {
            System.out.print(node.getVal() + "\t");
            preOrderTraverse(node.getLeft());
            preOrderTraverse(node.getRight());
        }
    }

    /**
     * 中序遍历：左父右
     *
     * @param node
     */
    public void inOrderTraverse(TreeNode node) {
        if (Objects.nonNull(node)) {
            inOrderTraverse(node.getLeft());
            System.out.print(node.getVal() + "\t");
            inOrderTraverse(node.getRight());
        }
    }

    /**
     * 后序遍历：左右父
     *
     * @param node
     */
    public void postOrderTraverse(TreeNode node) {
        if (Objects.nonNull(node)) {
            postOrderTraverse(node.getLeft());
            postOrderTraverse(node.getRight());
            System.out.print(node.getVal() + "\t");
        }
    }
}
