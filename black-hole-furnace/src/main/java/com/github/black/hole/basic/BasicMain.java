package com.github.black.hole.basic;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2021/8/31
 */
public class BasicMain {

    public static void main(String[] args) {
        List<Agency> list =
                Lists.newArrayList(
                        Agency.builder().id(1L).value("1").build(),
                        Agency.builder().id(1L).value("1").build(),
                        Agency.builder().id(1L).value("1").build(),
                        Agency.builder().id(2L).value("1").build(),
                        Agency.builder().id(2L).value("1").build(),
                        Agency.builder().id(2L).value("1").build(),
                        Agency.builder().id(2L).value("1").build());
//        Map<Long, BigDecimal> map =
//                list.stream()
//                        .collect(Collectors.groupingBy(Agency::getId,
//                                Collectors.mapping(i->new BigDecimal(i.getValue()),
//                                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
//        System.out.println(JSON.toJSONString(map));
        list.get(list.size()-1);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Agency {
        private Long id;
        private String value;
    }
}
