package com.github.black.hole.easy;

import com.alibaba.excel.write.handler.WriteHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author huanglongping
 * @date 2020/7/1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSheet<T> {
    /** 表单名称 */
    private String sheetName;
    /** 表单数据 */
    private List<T> sheetData;
    /** 表单数据class */
    private Class<T> sheetClass;
    /** 动态生成表头,为空默认按sheetClass类生成表头 */
    private List<List<String>> sheetHead;
    /** 指定样式，为空按默认样式 */
    private List<WriteHandler> writeHandlers;
}
