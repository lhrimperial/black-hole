package com.github.black.hole.easy;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author hairen.long
 * @date 2020/7/17
 */
public class EasyMain {

    public static void main(String[] args) throws Exception {
        test1();
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
