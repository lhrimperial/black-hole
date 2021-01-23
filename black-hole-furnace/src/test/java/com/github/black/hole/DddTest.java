package com.github.black.hole;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.ListUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
public class DddTest {

    /** 字符串拆分 */
    private static final String STRING_SPLIT = "[\\[,\\]]";

    public static void main(String[] args) {
        Long id = Long.valueOf("12");
    }

    private static boolean isContainKey(List<String> keys, String value) {
        if (CollectionUtils.isEmpty(keys) || Strings.isNullOrEmpty(value)) {
            return false;
        }
        return keys.stream().anyMatch(item -> value.contains(item));
    }

    public static <T> List<T> splitString2List(String str, Function<String, T> convertFunc) {
        if (Strings.isNullOrEmpty(str)) {
            return Collections.emptyList();
        }
        return Stream.of(str.split(STRING_SPLIT))
                .filter(item -> !Strings.isNullOrEmpty(item))
                .map(convertFunc)
                .collect(Collectors.toList());
    }

    private static void test1() {
        Map<Long, Set<Integer>> teamLabelMap = build();
        System.out.println(JSON.toJSONString(teamLabelMap));

        List<Integer> list1 = Lists.newArrayList(50, 10000);
        Predicate<Map.Entry<Long, Set<Integer>>> labelFilterFunc = getExcludeLabelPredicate(list1);
        teamLabelMap.entrySet().stream()
                .filter(labelFilterFunc)
                .forEach(
                        entry -> {
                            System.out.println(JSON.toJSONString(entry));
                        });
    }

    private static Map<Long, Set<Integer>> build() {
        Long[] teamIds = {
            57L, 3945L, 6996L, 19722L, 17424011L, 17430391L, 17431360L, 17434204L, 17436176L,
            17440853L, 17441011L, 45L, 85L, 3187L, 6908L, 20428L, 17408140L, 17430228L, 17430230L,
            17430231L, 17430234L
        };
        Integer[] code = {10, 20, 40, 80, 90, 110, 120, 150, 130, 140, 50, 10000};
        Random random = new Random();

        return Arrays.stream(teamIds)
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                teamId -> {
                                    int len = random.nextInt(5);
                                    Set<Integer> labels = Sets.newHashSet();
                                    for (int i = 0; i < len; i++) {
                                        labels.add(code[random.nextInt(code.length)]);
                                    }
                                    return labels;
                                },
                                (v1, v2) -> v1));
    }

    private static Predicate<Map.Entry<Long, Set<Integer>>> getExcludeLabelPredicate(
            List<Integer> ruleLabelCodes) {
        return teamLabelEntry -> {
            if (CollectionUtils.isEmpty(teamLabelEntry.getValue())) {
                return true;
            }
            return CollectionUtils.isEmpty(
                    ListUtils.intersection(
                            ruleLabelCodes, new ArrayList<>(teamLabelEntry.getValue())));
        };
    }

    private static void test2() {
        Set<Integer> set1 = Sets.newHashSet(50, 10000);
        Set<Integer> set2 = Sets.newHashSet(50, 80);
        System.out.println(Sets.intersection(set1, set2));

        List<Integer> list1 = Lists.newArrayList(50, 10000);
        List<Integer> list2 = Lists.newArrayList(50, 80);
        System.out.println(ListUtils.intersection(list1, list2));
    }

    private static void test() {
        List<Integer> list = IntStream.range(1, 10).boxed().collect(Collectors.toList());
        Map<Integer, BigDecimal> map =
                list.stream()
                        .collect(
                                Collectors.toMap(
                                        Function.identity(),
                                        item -> BigDecimal.ZERO,
                                        (v1, v2) -> v1));
        System.out.println(JSON.toJSONString(map));

        Map<String, String> map1 = Maps.newHashMap();
        map1.put("agencyId", "1");
        map1.put("userId", "1");
        map1.put("insuranceType", String.valueOf(1));
        map1.put("localInsuranceType", String.valueOf(1));
        map1.put("warringMsg", "warringMsg");
        System.out.println(JSON.toJSONString(map1));
    }
}
