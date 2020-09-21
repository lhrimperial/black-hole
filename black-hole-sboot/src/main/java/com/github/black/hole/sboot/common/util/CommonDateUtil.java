package com.github.black.hole.sboot.common.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/9/17
 */
public class CommonDateUtil {

    /**
     * 时间减去n年
     *
     * @param timestamp
     * @param year
     * @return
     */
    public static Timestamp minusYear(Timestamp timestamp, int year) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return Timestamp.valueOf(timestamp.toLocalDateTime().minusYears(year));
    }

    /**
     * 时间减去n月
     *
     * @param timestamp
     * @param month
     * @return
     */
    public static Timestamp minusMonth(Timestamp timestamp, int month) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return Timestamp.valueOf(timestamp.toLocalDateTime().minusMonths(month));
    }

    /**
     * 日期格式
     *
     * @param timestamp
     * @return
     */
    public static String formatDate(Timestamp timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return timestamp.toLocalDateTime().toLocalDate().toString();
    }

    /**
     * 获取周开始时间
     *
     * @return
     */
    public static Timestamp getStartOfWeek() {
        return Timestamp.valueOf(LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1).atStartOfDay());
    }
}
