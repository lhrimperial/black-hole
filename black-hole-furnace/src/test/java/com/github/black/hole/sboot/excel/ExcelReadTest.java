package com.github.black.hole.sboot.excel;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/2/24
 */
public class ExcelReadTest {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i : arr) {
            System.out.println(i & 0x03);
        }
    }

    @Test
    public void readExcel() throws Exception {
        String filePath = "质量稳定性-产品1.xlsx";
        URL resource = getClass().getClassLoader().getResource(filePath);
        try {
            InputStream inputStream =
                    new FileInputStream(
                            "/Users/eleme/Documents/idea_workspace/myself/github/black-hole/black-hole-furnace/src/test/resources/质量稳定性-产品1.xlsx");
            Workbook workbook = WorkbookFactory.create(inputStream);
            for (int i = 1; i <= 10; i++) {
                List<GradeStationDTO> stationLevels =
                        ExcelReadUtil.readObjectFromWorkBook(workbook, i, GradeStationDTO.class);
                System.out.println(JSON.toJSONString(stationLevels));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
