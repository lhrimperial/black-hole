package com.github.black.hole.easy;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author huanglongping
 * @date 2020/7/1
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class ActiveKnightExistRateSheet extends BaseDataSheet{
    /** 日期 yyyy-MM-dd */
    @ExcelProperty(
            value = {"日期"},
            index = 4)
    private String indexDate;
    /** 活跃骑手留存率 */
    @ExcelProperty(
            value = {"当前30日活跃骑手留存率"},
            index = 5)
    private String activeKnightExistRate;
    /** 30日活跃骑手数 */
    @ExcelProperty(
            value = {"30日活跃骑手数"},
            index = 6)
    private Integer activeKnightCount;
    /** 30日留存骑手数 */
    @ExcelProperty(
            value = {"30日留存骑手数"},
            index = 7)
    private Integer existKnightCount;
}
