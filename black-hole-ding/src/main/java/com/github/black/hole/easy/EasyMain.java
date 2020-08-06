package com.github.black.hole.easy;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author hairen.long
 * @date 2020/7/17
 */
public class EasyMain {

    public static void main(String[] args) throws Exception {
        test111();
    }

    public static void test111() throws Exception {
        ActiveKnightExistRateSheet sheet =
                ActiveKnightExistRateSheet.builder()
                        .buName("上海大区")
                        .cityName("上海")
                        .teamId(11L)
                        .teamName("站点")
                        .activeKnightCount(11)
                        .activeKnightExistRate("0.01")
                        .existKnightCount(11)
                        .indexDate("2020")
                        .build();

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        headWriteCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headWriteCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("等线");
        headWriteFont.setColor(IndexedColors.WHITE.getIndex());
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了
        // FillPatternType所以可以不指定
        //        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        contentWriteCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        ExcelSheet<ActiveKnightExistRateSheet> excelSheet =
                ExcelSheet.<ActiveKnightExistRateSheet>builder()
                        .sheetClass(ActiveKnightExistRateSheet.class)
                        .sheetData(Lists.newArrayList(sheet))
                        .writeHandlers(
                                Lists.newArrayList(
                                        horizontalCellStyleStrategy,
                                        new LongestMatchColumnWidthStyleStrategy()))
                        .sheetName("sheet1")
                        .build();
        byte[] data = ExcelUtil.writeOneSheet(excelSheet);
        OutputStream stream = new FileOutputStream("/Users/longhairen/Desktop/data11.xlsx");
        stream.write(data);
        stream.close();
    }

    public static void ExcelUtilTest() throws Exception {
        ScoreDetailSheet sheet =
                ScoreDetailSheet.builder()
                        .buName("上海大区")
                        .cityName("上海")
                        .agencyId(1L)
                        .agencyName("代理商")
                        .teamId(11L)
                        .teamName("站点")
                        .level("123")
                        .rank(123)
                        .totalScore("123")
                        .noSanctionKpi("123")
                        .noSanctionKpiScore("123")
                        .activeKnightExistRate("123")
                        .activeKnightExistRateScore("123")
                        .knightAttendanceRate("123")
                        .knightAttendanceRateScore("123")
                        .retailerComplainRate("123")
                        .retailerComplainRateScore("123")
                        .consumerComplainRate("123")
                        .consumerComplainRateScore("123")
                        .cheatingReportRate("123")
                        .cheatingReportRateScore("123")
                        .violationRate("123")
                        .violationRateScore("123")
                        .knightComplainRate("123")
                        .knightComplainRateScore("123")
                        .meetingRate("123")
                        .meetingRateScore("123")
                        .equipmentQualifiedRate("123")
                        .equipmentQualifiedRateScore("123")
                        .examination("123")
                        .examinationScore("123")
                        .trainQualifiedRate("123")
                        .trainQualifiedRateScore("123")
                        .accidentManager("123")
                        .accidentManagerScore("123")
                        .capacityFluctuateCount("123")
                        .capacityFluctuateCountScore("123")
                        .build();

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        headWriteCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headWriteCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("等线");
        headWriteFont.setColor(IndexedColors.WHITE.getIndex());
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了
        // FillPatternType所以可以不指定
        //        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        contentWriteCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        contentWriteCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        ExcelSheet<ScoreDetailSheet> excelSheet =
                ExcelSheet.<ScoreDetailSheet>builder()
                        .sheetClass(ScoreDetailSheet.class)
                        .sheetData(Lists.newArrayList(sheet))
                        .sheetHead(ScoreDetailSheet.sheetHead(Maps.newHashMap()))
                        .sheetName("")
                        .build();
        byte[] data = null;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ExcelWriter excelWriter = EasyExcelFactory.write(os).build();
            WriteSheet writeSheet =
                    EasyExcelFactory.writerSheet("sheet1")
                            .head(ScoreDetailSheet.sheetHead(Maps.newHashMap()))
                            .registerWriteHandler(horizontalCellStyleStrategy)
                            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                            .build();

            excelWriter.write(Lists.newArrayList(sheet, sheet), writeSheet);
            excelWriter.finish();

            data = os.toByteArray();
        } catch (Exception e) {
            throw new Exception("excel写入异常");
        }
        OutputStream stream = new FileOutputStream("/Users/longhairen/Desktop/data.xlsx");
        stream.write(data);
        stream.close();
    }

    public static void test() throws Exception {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String filePath = "/Users/longhairen/Desktop/demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        List<DemoData> dataList =
                EasyExcel.read(filePath, DemoData.class, new DemoDataListener())
                        .sheet()
                        .doReadSync();
        System.out.println(dataList);
    }

    public static void test1() throws Exception {
        String filePath = "/Users/longhairen/Desktop/demo.xlsx";
        ByteArrayInputStream bis =
                new ByteArrayInputStream(Files.readAllBytes(Paths.get(filePath)));
        List<DemoData> dataList =
                EasyExcelFactory.read(bis).head(DemoData.class).sheet("Sheet1").doReadSync();
        System.out.println(dataList);
    }
}
