package com.github.black.hole;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2020/11/3
 */
public class StreamTest {

    public static void main(String[] args) {
        int[] list =
                IntStream.rangeClosed(1, 0)
                        .filter(Objects::nonNull)
                        .filter(item -> item > 5)
                        .map(item -> item * 2)
                        .toArray();
        System.out.println(Arrays.toString(list));
    }
}
