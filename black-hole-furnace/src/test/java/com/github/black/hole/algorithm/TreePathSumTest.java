package com.github.black.hole.algorithm;

import com.alibaba.fastjson.JSON;
import com.github.black.hole.base.algorithm.tree.BinaryTree;
import com.github.black.hole.base.algorithm.tree.TreeNode;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2020/10/27
 */
public class TreePathSumTest {

    @Test
    public void test() {
        int[] arr = {11, 10, 8, 12, 7, 13};
        BinaryTree tree = new BinaryTree();
        for (int i = 0, len = arr.length; i < len; i++) {
            tree.insert(arr[i]);
        }
        System.out.println("前序遍历：");
        tree.preOrderTraverse(tree.getRootNode());
        System.out.println();
        List<List<Integer>> ans = solution(tree.getRootNode(), 36);
        ans.forEach(item -> System.out.println(JSON.toJSONString(item)));
    }

    public List<List<Integer>> solution(TreeNode node, int sum) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> answer = Lists.newArrayList();
        pathSum(node, sum, new LinkedList<Integer>(), answer);
        return answer;
    }

    public void pathSum(
            TreeNode node, int target, LinkedList<Integer> curr, List<List<Integer>> answer) {
        if (node == null) {
            return;
        }
        curr.add(node.getVal());
        if (target - node.getVal() == 0 && node.getLeft() == null && node.getRight() == null) {
            answer.add(Lists.newArrayList(curr));
        }
        pathSum(node.getLeft(), target - node.getVal(), curr, answer);
        pathSum(node.getRight(), target - node.getVal(), curr, answer);
        curr.removeLast();
    }
}
