package com.github.black.hole;

import org.apache.poi.ss.formula.functions.T;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.sql.Timestamp;

/**
 * @author hairen.long
 * @date 2021/1/20
 */
public class LongTest {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Long[] arr = new Long[10000];
        for (int i = 0, len = arr.length; i < len; i++) {
            arr[i] = (long) i;
        }
        // 获取对象总大小
        Long a = 1L;
        String info = GraphLayout.parseInstance(a).toPrintable();
        System.out.println(info);
        long size = GraphLayout.parseInstance(a).totalSize();
        System.out.println("size : " + size);
    }
}
