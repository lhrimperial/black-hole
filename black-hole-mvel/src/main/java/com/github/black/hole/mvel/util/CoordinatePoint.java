package com.github.black.hole.mvel.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author hairen.long
 * @date 2020/7/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatePoint {
    /** 坐标(x) */
    private BigDecimal x;
    /** 坐标(y) */
    private BigDecimal y;
    /** 开闭区间 0 开区间 1 闭区间 */
    private Integer interval;
}
