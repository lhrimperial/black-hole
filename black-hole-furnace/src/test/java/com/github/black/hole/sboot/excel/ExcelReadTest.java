package com.github.black.hole.sboot.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author hairen.long
 * @date 2021/2/24
 */
public class ExcelReadTest {

    @Test
    public void readExcel() throws Exception {
        String filePath = "质量稳定性-产品1.xlsx";
        URL resource = getClass().getClassLoader().getResource(filePath);
        try {
            InputStream inputStream  = new ByteArrayInputStream(Files.readAllBytes(Paths.get(resource.toURI())));
            Workbook workbook = WorkbookFactory.create(inputStream);
            for (int i = 1; i <= 10; i++) {
                Sheet sheet = workbook.getSheetAt(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
