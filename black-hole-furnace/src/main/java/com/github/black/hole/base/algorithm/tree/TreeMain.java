package com.github.black.hole.base.algorithm.tree;

/**
 * @author hairen.long
 * @date 2020/10/16
 */
public class TreeMain {

    public static void main(String[] args) {
        traverse();
    }

    public static void traverse() {
        int[] arr = {8, 5, 2, 1, 3, 4, 6, 7, 9};
        BinaryTree tree = new BinaryTree();
        for (int i = 0, len = arr.length; i < len; i++) {
            tree.insert(arr[i]);
        }
        System.out.println("前序遍历：");
        tree.preOrderTraverse(tree.getRootNode());

        System.out.println("\n中序遍历：");
        tree.inOrderTraverse(tree.getRootNode());

        System.out.println("\n后序遍历：");
        tree.postOrderTraverse(tree.getRootNode());
    }
}
