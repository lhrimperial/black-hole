package com.github.black.hole.sboot.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.AbstractRowHeightStyleStrategy;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * @author hairen.long
 * @date 2021/8/12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoSheet {

    /** 资源ID */
    @ExcelProperty(
            value = {
                "1.生效日期可不填，表示立即生效，格式：yyyy-MM-dd \n2.可绑定角色：团队配送经理(64)、众包配送经理(72)、品牌配送经理(131)、业务督导(80)、合规督导(107)",
                "资源ID"
            },
            index = 0)
    private Long resourceId;
    /** 资源名称 */
    @ExcelProperty(
            value = {
                "1.生效日期可不填，表示立即生效，格式：yyyy-MM-dd \n2.可绑定角色：团队配送经理(64)、众包配送经理(72)、品牌配送经理(131)、业务督导(80)、合规督导(107)",
                "资源名称"
            },
            index = 1)
    private String resourceName;
    /** 资源类型 */
    @ExcelProperty(
            value = {
                "1.生效日期可不填，表示立即生效，格式：yyyy-MM-dd \n2.可绑定角色：团队配送经理(64)、众包配送经理(72)、品牌配送经理(131)、业务督导(80)、合规督导(107)",
                "资源类型"
            },
            index = 2)
    private Integer resourceType;
    /**
     * 生成excel样式
     *
     * @return
     */
    public static List<WriteHandler> excelCellStyle() {
        // 头的策略
        WriteCellStyle headStyle = new WriteCellStyle();
        headStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        headStyle.setTopBorderColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headStyle.setBottomBorderColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headStyle.setLeftBorderColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headStyle.setRightBorderColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);

        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("等线");
        headWriteFont.setColor(IndexedColors.WHITE.getIndex());
        headWriteFont.setFontHeightInPoints((short) 12);
        headStyle.setWriteFont(headWriteFont);
        headStyle.setWrapped(true);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        return Lists.newArrayList(
                new HorizontalCellStyleStrategy(headStyle, contentWriteCellStyle),
                new SimpleColumnWidthStyleStrategy(30),
                new CustomRowHeightStyleStrategy((short) 20, (short) 20));
    }

    static class CustomRowHeightStyleStrategy extends AbstractRowHeightStyleStrategy {
        private Short headRowHeight;
        private Short contentRowHeight;

        public CustomRowHeightStyleStrategy(Short headRowHeight, Short contentRowHeight) {
            this.headRowHeight = headRowHeight;
            this.contentRowHeight = contentRowHeight;
        }

        @Override
        protected void setHeadColumnHeight(Row row, int relativeRowIndex) {
            if (headRowHeight != null) {
                row.setHeightInPoints(headRowHeight);
                // 首行高度定义
                if (relativeRowIndex == 0) {
                    row.setHeightInPoints(100);
                }
            }
        }

        @Override
        protected void setContentColumnHeight(Row row, int relativeRowIndex) {
            if (contentRowHeight != null) {
                row.setHeightInPoints(contentRowHeight);
            }
        }
    }
}
