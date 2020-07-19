package com.github.black.hole;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2020/5/11
 */
public class MainTest {

    public static void main(String[] args) {
        IntStream.range(3,19).forEach(i->System.out.println(i));
    }

    public static void test() {
        Integer[] num = {
            1, 1, 5, 6, 7, 8, 11, 11, 15, 15, 15, 16, 16, 19, 20, 20, 20, 21, 21, 22, 22, 23, 23,
            23, 29, 31, 32, 35, 38, 38, 39, 40, 40, 40, 41, 43, 44, 44, 44, 44, 44, 44, 44, 44, 44,
            44, 44, 45, 45, 47, 47, 47, 47, 48, 50, 50, 58, 63, 70, 70, 70, 71, 82, 87, 90, 92, 95,
            95, 95, 95, 95, 95, 101, 104, 104, 106, 111, 119, 119, 124, 124, 124, 128, 128, 128,
            129, 129, 134, 137, 137, 145, 145, 154, 157, 157, 168, 170, 170, 170, 181, 183, 194,
            195, 195, 195, 196, 196, 196, 198, 198, 198, 200, 200, 218, 218, 220, 222, 222, 222,
            233, 236, 246, 246, 252, 252, 252, 252, 253, 273, 290, 290, 291, 323, 323, 337, 339,
            361, 397, 397, 397, 398, 399, 399, 399, 399, 399, 421, 581, 582, 1175, 1175, 1175, 1403,
            1844, 1938, 1938, 1938, 2480, 2480, 2480, 5896
        };
        Set<Integer> sets = Arrays.stream(num).collect(Collectors.toSet());
        System.out.println(JSON.toJSONString(sets));
    }
}
