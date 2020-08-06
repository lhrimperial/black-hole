package com.github.black.hole.easy;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author hairen.long
 * @date 2020/7/30
 */
@SuperBuilder
@NoArgsConstructor
public class BaseDataSheet {
    /** 大区名称 */
    @ExcelProperty(
            value = {"大区"},
            index = 0)
    private String buName;
    /** 城市名称 */
    @ExcelProperty(
            value = {"城市"},
            index = 1)
    private String cityName;
    /** 站点id */
    @ExcelProperty(
            value = {"站点ID"},
            index = 2)
    private Long teamId;
    /** 站点名称 */
    @ExcelProperty(
            value = {"站点名称"},
            index = 3)
    private String teamName;
}
