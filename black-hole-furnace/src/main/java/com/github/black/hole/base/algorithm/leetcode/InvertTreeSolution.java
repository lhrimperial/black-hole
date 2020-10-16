package com.github.black.hole.base.algorithm.leetcode;

import com.alibaba.fastjson.JSON;
import com.github.black.hole.base.algorithm.tree.BinaryTree;
import com.github.black.hole.base.algorithm.tree.TreeNode;

import java.util.List;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/10/16
 */
public class InvertTreeSolution {

    public static void main(String[] args) {
        int[] arr = {11, 8, 3, 5, 4, 12, 14, 7, 2, 13, 21};
        BinaryTree tree = new BinaryTree();
        for (int i = 0, len = arr.length; i < len; i++) {
            tree.insert(arr[i]);
        }
        System.out.println("前序遍历：");
        tree.preOrderTraverse(tree.getRootNode());
        System.out.println();
        invertTree(tree.getRootNode());
        System.out.println("前序遍历：");
        tree.preOrderTraverse(tree.getRootNode());
    }

    public static void invertTree(TreeNode node) {
        if (Objects.isNull(node)) {
            return;
        }
        TreeNode temp = node.getLeft();
        node.setLeft(node.getRight());
        node.setRight(temp);
        invertTree(node.getLeft());
        invertTree(node.getRight());
    }


}
