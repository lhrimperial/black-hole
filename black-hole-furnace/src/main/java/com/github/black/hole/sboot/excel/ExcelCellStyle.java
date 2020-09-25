package com.github.black.hole.sboot.excel;

import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/9/21
 */
public class ExcelCellStyle {

    /**
     * 生成excel样式
     *
     * @return
     */
    public static List<WriteHandler> excelCellStyle() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        headWriteCellStyle.setTopBorderColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headWriteCellStyle.setBottomBorderColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headWriteCellStyle.setLeftBorderColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headWriteCellStyle.setRightBorderColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("等线");
        headWriteFont.setColor(IndexedColors.WHITE.getIndex());
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        return Lists.newArrayList(
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle),
                new LongestMatchColumnWidthStyleStrategy());
    }
}
