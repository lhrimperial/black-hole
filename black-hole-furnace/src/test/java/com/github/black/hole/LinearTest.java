package com.github.black.hole;

import com.github.black.hole.sboot.linear.CoordinatePoint;
import com.github.black.hole.sboot.linear.LinearCalcUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2020/12/11
 */
public class LinearTest {

    public static void main(String[] args) {
        test11();
    }

    public static void test11(){
        String express = "id235+id99-id107-id123";
        Map<String, Object> map = Maps.newHashMap();
        map.put("id235", new BigDecimal(4.3));
        map.put("id99", new BigDecimal(5));
        map.put("id107", new BigDecimal(0));
        map.put("id123", new BigDecimal(0));
        BigDecimal value = LinearCalcUtil.calcExpression(express, map);
        System.out.println(value);
    }

    public static void test(){
        List<BigDecimal> result =
                IntStream.rangeClosed(1, 5555)
                        .mapToObj(id -> calcPercentage2(id, 5555))
                        .collect(Collectors.toList());
        List<CoordinatePoint> lines = buildLines();
        AtomicInteger count = new AtomicInteger();
        result.forEach(
                item -> {
                    count.getAndIncrement();
                    BigDecimal r = LinearCalcUtil.matchInterval(item, lines);
                    System.out.println("x=" + item + ",y=" + r.toString());
                });
        System.out.println("count=" + count);
    }

    public static BigDecimal calcPercentage2(Integer molecule, Integer denominator) {
        return BigDecimal.valueOf(molecule)
                .divide(BigDecimal.valueOf(denominator), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calcPercentage4(Integer molecule, Integer denominator) {
        return BigDecimal.valueOf(molecule)
                .divide(BigDecimal.valueOf(denominator), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public static List<CoordinatePoint> buildLines() {
        return Lists.newArrayList(
                CoordinatePoint.builder()
                        .x(BigDecimal.valueOf(0))
                        .y(BigDecimal.valueOf(2))
                        .interval(1)
                        .build(),
                CoordinatePoint.builder()
                        .x(BigDecimal.valueOf(10))
                        .y(BigDecimal.valueOf(2))
                        .interval(1)
                        .build(),
                CoordinatePoint.builder()
                        .x(BigDecimal.valueOf(10))
                        .y(BigDecimal.valueOf(10))
                        .interval(0)
                        .build(),
                CoordinatePoint.builder()
                        .x(BigDecimal.valueOf(30))
                        .y(BigDecimal.valueOf(10))
                        .interval(1)
                        .build(),
                CoordinatePoint.builder()
                        .x(BigDecimal.valueOf(30))
                        .y(BigDecimal.valueOf(18))
                        .interval(0)
                        .build(),
                CoordinatePoint.builder()
                        .x(BigDecimal.valueOf(70))
                        .y(BigDecimal.valueOf(18))
                        .interval(1)
                        .build(),
                CoordinatePoint.builder()
                        .x(BigDecimal.valueOf(70))
                        .y(BigDecimal.valueOf(26))
                        .interval(0)
                        .build(),
                CoordinatePoint.builder()
                        .x(BigDecimal.valueOf(90))
                        .y(BigDecimal.valueOf(26))
                        .interval(1)
                        .build(),
                CoordinatePoint.builder()
                        .x(BigDecimal.valueOf(90))
                        .y(BigDecimal.valueOf(34))
                        .interval(0)
                        .build(),
                CoordinatePoint.builder()
                        .x(BigDecimal.valueOf(100))
                        .y(BigDecimal.valueOf(34))
                        .interval(1)
                        .build());
    }
}