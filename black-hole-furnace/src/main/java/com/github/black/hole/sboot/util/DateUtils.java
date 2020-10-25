package com.github.black.hole.sboot.util;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @author hairen.long
 * @date 2020/10/23
 */
public class DateUtils {

    /**
     * 判断日期是不是今天
     *
     * @param timestamp
     * @return
     */
    public static boolean isToday(Timestamp timestamp) {
        LocalDate today = LocalDate.now();
        LocalDate date = timestamp.toLocalDateTime().toLocalDate();
        return today.compareTo(date) == 0;
    }

    public static void main(String[] args) {
        System.out.println(isToday(Timestamp.valueOf("2020-10-24 12:00:00")));
    }

}
