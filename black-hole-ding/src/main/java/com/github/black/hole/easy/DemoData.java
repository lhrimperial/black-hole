package com.github.black.hole.easy;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hairen.long
 * @date 2020/7/17
 */
@Data
public class DemoData {
    @ExcelProperty("字符串表头")
    private String name;

    @ExcelProperty("日期表头")
    private Date date;

    @ExcelProperty("数字表头")
    private BigDecimal data;

    @ExcelProperty("测试")
    private String testName;

    @ExcelProperty("测试1")
    private BigDecimal testName1;

    @ExcelProperty("测试2")
    private String testName2;
}
