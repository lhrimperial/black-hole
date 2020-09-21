package com.github.black.hole.sboot.common.util;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/9/17
 */
public class CommonDateUtil {

    public static Timestamp minusYear(Timestamp timestamp, int year) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return Timestamp.valueOf(timestamp.toLocalDateTime().minusYears(year));
    }

    public static Timestamp minusMonth(Timestamp timestamp, int month) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return Timestamp.valueOf(timestamp.toLocalDateTime().minusMonths(month));
    }

    public static String formatDate(Timestamp timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return timestamp.toLocalDateTime().toLocalDate().toString();
    }
}
