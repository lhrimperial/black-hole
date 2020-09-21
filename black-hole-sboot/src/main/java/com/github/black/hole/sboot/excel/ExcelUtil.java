package com.github.black.hole.sboot.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.github.black.hole.sboot.common.ServiceException;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/8/30
 */
@UtilityClass
public class ExcelUtil {

    /**
     * 写入单个sheet
     *
     * @param sheet
     * @return
     */
    public byte[] writeOneSheet(ExcelSheet<?> sheet) throws ServiceException {
        if (Objects.isNull(sheet)) {
            return new byte[0];
        }

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ExcelWriter excelWriter = EasyExcelFactory.write(os).build();
            writeSheet(excelWriter, sheet);
            excelWriter.finish();
            return os.toByteArray();
        } catch (Exception e) {
            throw new ServiceException("excel写入异常");
        }
    }

    /**
     * 写入多个sheet
     *
     * @param sheets
     * @return
     */
    public byte[] writeSheets(List<ExcelSheet<?>> sheets) throws ServiceException {
        if (CollectionUtils.isEmpty(sheets)) {
            return new byte[0];
        }

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ExcelWriter excelWriter = EasyExcelFactory.write(os).build();
            for (ExcelSheet<?> sheet : sheets) {
                writeSheet(excelWriter, sheet);
            }
            excelWriter.finish();
            return os.toByteArray();
        } catch (Exception e) {
            throw new ServiceException("excel写入异常");
        }
    }

    /**
     * 同步读取excel （同步读取，建议excel文件比较小时使用）
     *
     * @param data
     * @param sheetName
     * @param sheetClass
     * @param <T>
     * @return
     */
    public <T> List<T> synchronousReadSheet(byte[] data, String sheetName, Class<T> sheetClass) {
        if (null == data) {
            return Collections.emptyList();
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        return EasyExcelFactory.read(bis).head(sheetClass).sheet(sheetName).doReadSync();
    }

    /**
     * 异步读取Excel
     *
     * @param data
     * @param sheetName
     * @param sheetClass
     * @param readListener Excel读取监听器
     * @param <T>
     */
    public <T> void asynchronousReadSheet(
            byte[] data, String sheetName, Class<T> sheetClass, ReadListener<T> readListener) {
        if (null == data) {
            return;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        EasyExcelFactory.read(bis)
                .head(sheetClass)
                .sheet(sheetName)
                .registerReadListener(readListener)
                .doRead();
    }

    private void writeSheet(ExcelWriter excelWriter, ExcelSheet<?> sheet) {
        ExcelWriterSheetBuilder builder =
                EasyExcelFactory.writerSheet(sheet.getSheetName())
                        .head(sheet.getSheetClass())
                        .head(sheet.getSheetHead());
        List<WriteHandler> writeHandlers = sheet.getWriteHandlers();
        if (!CollectionUtils.isEmpty(writeHandlers)) {
            for (WriteHandler writeHandler : writeHandlers) {
                builder.registerWriteHandler(writeHandler);
            }
        }
        excelWriter.write(sheet.getSheetData(), builder.build());
    }
}
