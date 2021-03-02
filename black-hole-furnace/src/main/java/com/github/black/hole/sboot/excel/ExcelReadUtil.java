package com.github.black.hole.sboot.excel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Closeables;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/5/21
 */
public class ExcelReadUtil {

    public static Workbook readWorkbook(String path) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(Files.readAllBytes(Paths.get(path)));
            return WorkbookFactory.create(inputStream);
        } finally {
            Closeables.closeQuietly(inputStream);
        }
    }

    public static <T> List<T> readObject(String path, Class<T> clazz) throws Exception {
        Workbook workbook = readWorkbook(path);
        return readObjectFromWorkBook(workbook, 0, clazz);
    }

    public static <T> List<T> readObject(String path, int sheetId, Class<T> clazz)
            throws Exception {
        Workbook workbook = readWorkbook(path);
        return readObjectFromWorkBook(workbook, sheetId, clazz);
    }

    private static <T> List<T> readObjectFromWorkBook(
            Workbook workbook, int sheetId, Class<T> clazz) throws Exception {
        List<T> resultList = Lists.newArrayList();
        Field[] fields = clazz.getDeclaredFields();

        Sheet sheet = workbook.getSheetAt(sheetId);
        Row header = sheet.getRow(0);
        int firstColumn = header.getFirstCellNum();
        int lastColumn = header.getLastCellNum();
        for (int rInx = sheet.getFirstRowNum() + 1; rInx <= sheet.getLastRowNum(); rInx++) {
            Row row = sheet.getRow(rInx);
            Map<String, String> fieldValue = Maps.newHashMap();

            for (int cInx = firstColumn; cInx < lastColumn; cInx++) {
                fieldValue.put(fields[cInx].getName(), getValue(row.getCell(cInx)));
            }

            @SuppressWarnings("unchecked")
            Constructor<T> con =  clazz.getConstructor();
            @SuppressWarnings("unchecked")
            T instance = con.newInstance();
            Arrays.stream(fields)
                    .forEach(
                            (field) -> {
                                Object value =
                                        convert2Value(
                                                field.getType(), fieldValue.get(field.getName()));
                                if (!Objects.isNull(value)) {
                                    field.setAccessible(true);
                                    setField(field, instance, value);
                                }
                            });
            resultList.add(instance);
        }
        return resultList;
    }

    public static String getValue(Cell cell) {
        String value = "";
        if (null == cell) {
            return value;
        }
        switch (cell.getCellType()) {
                // 数值型
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是date类型则 ，获取该cell的date值
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = format.format(date);
                } else { // 纯数字
                    BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();
                }
                break;
                // 公式类型
            case FORMULA:
                // 读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                if (value.equals("NaN")) {
                    // 如果获取的数据值为非法值,则转换为获取字符串
                    value = cell.getStringCellValue();
                }
                break;
                // 布尔类型
            case BOOLEAN:
                value = " " + cell.getBooleanCellValue();
                break;
                // 空值
            case BLANK:
                // 故障
            case ERROR:
                value = "";
                break;
                // 字符串类型
            case STRING:
            default:
                value = cell.getStringCellValue();
        }
        return value;
    }

    private static void setField(Field field, Object target, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException var4) {
            handleReflectionException(var4);
        }
    }

    private static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found Exception: " + ex.getMessage());
        } else if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException(
                    "Could not access method Exception: " + ex.getMessage());
        } else if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        } else {
            throw new UndeclaredThrowableException(ex);
        }
    }

    private static Object convert2Value(Class<?> classType, Object obj) {
        if (!Objects.isNull(obj)) {
            String str = obj.toString();
            if (classType.equals(String.class)) {
                return String.valueOf(str);
            }
            if (classType.equals(Long.class)) {
                return Long.valueOf(str);
            }
            if (classType.equals(Integer.class)) {
                BigDecimal value = new BigDecimal(str);
                return value.intValue();
            }
            if (classType.equals(Double.class)) {
                BigDecimal value = new BigDecimal(str);
                return value.doubleValue();
            }
        }
        return null;
    }
}
