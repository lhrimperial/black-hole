package com.github.black.hole.base.algorithm.leetcode;

import com.alibaba.fastjson.JSON;
import com.github.black.hole.base.algorithm.tree.BinaryTree;
import com.github.black.hole.base.algorithm.tree.TreeNode;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/10/16
 */
public class PathSumSolution {

    public static void main(String[] args) {
        int[] arr = {11, 8, 3, 5, 4, 12, 14, 7, 2, 13, 21};
        BinaryTree tree = new BinaryTree();
        for (int i = 0, len = arr.length; i < len; i++) {
            tree.insert(arr[i]);
        }
        System.out.println("前序遍历：");
        tree.preOrderTraverse(tree.getRootNode());
        System.out.println();
        List<List<Integer>> ans = solution(tree.getRootNode(), 34);
        ans.forEach(item -> System.out.println(JSON.toJSONString(item)));
    }

    public static List<List<Integer>> solution(TreeNode node, int sum) {
        if (Objects.isNull(node)) {
            return Collections.emptyList();
        }
        List<List<Integer>> answer = Lists.newArrayList();
        pathSum(node, sum, new LinkedList<Integer>(), answer);
        return answer;
    }

    private static void pathSum(
            TreeNode node, int target, LinkedList<Integer> curr, List<List<Integer>> answer) {
        if (Objects.isNull(node)) {
            return;
        }
        curr.add(node.getVal());
        if (target - node.getVal() == 0
                && Objects.isNull(node.getLeft())
                && Objects.isNull(node.getRight())) {
            answer.add(Lists.newArrayList(curr));
        }
        target -= node.getVal();
        pathSum(node.getLeft(), target, curr, answer);
        pathSum(node.getRight(), target, curr, answer);
        curr.removeLast();
    }


}
