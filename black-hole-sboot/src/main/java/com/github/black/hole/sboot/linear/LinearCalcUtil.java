package com.github.black.hole.sboot.linear;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.mvel2.MVEL;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2020/7/20
 */
public class LinearCalcUtil {

    /** 斜率计算公式 */
    public static final String EXPRESSION = "((y2-y1)/(x2-x1))*(x3-x2)+y2";

    /**
     * 分段函数
     *
     * @param xValue x值
     * @param coordinateLines 坐标线段 必须连续
     * @return
     */
    public static BigDecimal matchInterval(
            BigDecimal xValue, List<CoordinatePoint> coordinateLines) {
        coordinateLines =
                Optional.ofNullable(coordinateLines).orElse(Collections.emptyList()).stream()
                        .filter(Objects::nonNull)
                        .sorted(Comparator.comparing(CoordinatePoint::getX))
                        .collect(Collectors.toList());
        // 左端不包含x
        if (xValue.compareTo(coordinateLines.get(0).getX()) < 0) {
            return BigDecimal.ZERO;
        }
        // 最右端不包含x
        int lineSize = coordinateLines.size();
        if (xValue.compareTo(coordinateLines.get(lineSize - 1).getX()) > 0) {
            return BigDecimal.ZERO;
        }

        for (int i = 0, len = coordinateLines.size(); i < len; i++) {
            CoordinatePoint point = coordinateLines.get(i);
            // x在具体点上
            boolean res = xValue.compareTo(point.getX()) == 0;
            boolean interval =
                    Objects.equals(point.getInterval(), LineIntervalEnum.CLOSE.getCode());
            if (res && interval) {
                return point.getY();
            }
            if (i + 1 < len) {
                // x3在线段区间内
                boolean conditionFir = xValue.compareTo(point.getX()) > 0;

                CoordinatePoint nextPoint = coordinateLines.get(i + 1);
                boolean conditionSec = xValue.compareTo(nextPoint.getX()) < 0;

                if (conditionFir && conditionSec) {
                    return point.getY();
                }
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * 计算表达式
     *
     * @param expression
     * @param paramMap
     * @return
     */
    public static BigDecimal calcExpression(String expression, Map<String, Object> paramMap) {
        if (Strings.isNullOrEmpty(expression) || CollectionUtils.isEmpty(paramMap)) {
            return null;
        }
        try {
            Object y3 = MVEL.eval(expression, paramMap);
            return new BigDecimal(y3.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 线性y坐标
     *
     * @param points 线段点坐标
     * @param x3 x3
     * @return y3
     */
    public static BigDecimal calcCoordinate(List<CoordinatePoint> points, BigDecimal x3) {
        try {
            if (Objects.isNull(x3)) {
                // 如果对应的指标值为空 兜底值为0
                x3 = BigDecimal.ZERO;
            }
            BigDecimal x3Value = x3;
            // 判断指标属于哪个区间
            List<BigDecimal> indexValues =
                    points.stream()
                            .sorted(Comparator.comparing(CoordinatePoint::getX))
                            .map(CoordinatePoint::getX)
                            .collect(Collectors.toList());
            // 运营配置最左端不包含x3
            if (x3Value.compareTo(indexValues.get(0)) < 0) {
                return BigDecimal.ZERO;
            }
            // 运营配置最右端不包含x3
            if (x3Value.compareTo(indexValues.get(indexValues.size() - 1)) > 0) {
                return BigDecimal.ZERO;
            }

            for (int i = 0; i < indexValues.size(); i++) {
                // x3在具体点上
                boolean res = x3Value.compareTo(indexValues.get(i)) == 0;
                boolean interval =
                        Objects.equals(
                                points.get(i).getInterval(), LineIntervalEnum.CLOSE.getCode());
                if (res && interval) {
                    return points.get(i).getY();
                }
                if (i + 1 < indexValues.size()) {
                    // x3在线段区间内
                    boolean conditionFir = x3Value.compareTo(indexValues.get(i)) > 0;

                    boolean conditionSec = x3Value.compareTo(indexValues.get(i + 1)) < 0;

                    if (conditionFir && conditionSec) {
                        BigDecimal x1 = points.get(i).getX();
                        BigDecimal y1 = points.get(i).getY();
                        BigDecimal x2 = points.get(i + 1).getX();
                        BigDecimal y2 = points.get(i + 1).getY();
                        Map<String, Object> paramMap = Maps.newHashMap();
                        paramMap.put("y2", y2);
                        paramMap.put("y1", y1);
                        paramMap.put("x2", x2);
                        paramMap.put("x1", x1);
                        paramMap.put("x3", x3Value);
                        Object y3 = MVEL.eval(EXPRESSION, paramMap);
                        return new BigDecimal(y3.toString());
                    }
                }
            }
            return BigDecimal.ZERO;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}
