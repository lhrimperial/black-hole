package com.github.black.hole.algorithm;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2021/4/4
 */
public class StreamTest {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("bcd", "cde", "def", "abc");
        List<String> result =
                list.stream()
                        // .parallel()
                        .filter(e -> e.length() >= 3)
                        .map(e -> e.charAt(0))
                        // .peek(System.out :: println)
                        // .sorted()
                        // .peek(e -> System.out.println("++++" + e))
                        .map(String::valueOf)
                        .collect(Collectors.toList());
        System.out.println("----------------------------");
        System.out.println(result);
    }
}
