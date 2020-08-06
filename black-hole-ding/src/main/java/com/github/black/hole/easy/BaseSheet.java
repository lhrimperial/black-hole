package com.github.black.hole.easy;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author huanglongping
 * @date 2020/7/2
 *     <p>默认表单样式：
 *     <p>HeadRowHeight：表头高度设置
 *     <p>HeadStyle:表头样式设置 背景颜色 IndexedColors.RED.getIndex()
 *     <p>HeadFontStyle：表头字体设置
 *     <p>ContentRowHeight：内容高度设置
 *     <p>ColumnWidth：列宽设置
 *     <p>ContentStyle：内容样式设置
 *     <p>ContentFontStyle：内容字体设置
 */
@HeadStyle(fillBackgroundColor = 44)
@HeadFontStyle(fontHeightInPoints = 12)
@ContentRowHeight(20)
@ColumnWidth(25)
@ContentStyle(fillPatternType = FillPatternType.NO_FILL, fillForegroundColor = 9)
@ContentFontStyle(fontHeightInPoints = 14)
public abstract class BaseSheet {}
