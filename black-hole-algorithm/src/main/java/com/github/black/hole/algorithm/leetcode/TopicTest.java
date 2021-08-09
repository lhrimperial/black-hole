package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/2/19
 */
public class TopicTest {

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 8};
        int aa = Arrays.stream(arr).reduce(0, (t1, t2) -> t1 | t2);
        System.out.println(aa);
    }


}
