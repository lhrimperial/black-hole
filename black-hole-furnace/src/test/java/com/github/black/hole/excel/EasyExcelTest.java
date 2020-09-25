package com.github.black.hole.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.github.black.hole.sboot.excel.ExcelCellStyle;
import com.github.black.hole.sboot.excel.ExcelSheet;
import com.github.black.hole.sboot.excel.ExcelUtil;
import com.google.common.collect.Lists;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author hairen.long
 * @date 2020/9/21
 */
public class EasyExcelTest {

    public static void writeTest() throws Exception {
        List<DemoData> sheet = Lists.newArrayList(DemoData.builder().build());

        ExcelSheet<DemoData> excelSheet =
                ExcelSheet.<DemoData>builder()
                        .sheetClass(DemoData.class)
                        .sheetData(Lists.newArrayList(sheet))
                        .writeHandlers(ExcelCellStyle.excelCellStyle())
                        .sheetName("sheet1")
                        .build();
        byte[] data = ExcelUtil.writeOneSheet(excelSheet);
        OutputStream stream = new FileOutputStream("/Users/longhairen/Desktop/data11.xlsx");
        stream.write(data);
    }

    public static void readTest1() throws Exception {
        String filePath = "/Users/longhairen/Desktop/demo.xlsx";
        ByteArrayInputStream bis =
                new ByteArrayInputStream(Files.readAllBytes(Paths.get(filePath)));
        List<DemoData> dataList =
                EasyExcelFactory.read(bis).head(DemoData.class).sheet("Sheet1").doReadSync();
        System.out.println(dataList);
    }
}
