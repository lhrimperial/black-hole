package com.github.black.hole;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.mvel2.MVEL;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2020/11/3
 */
public class StreamTest {

    public static void main(String[] args) {
        String sourceIdentify = "CROWD_LAYERED_GROUP_11799027";
        if (Strings.isNullOrEmpty(sourceIdentify)) {
            return;
        }
        int index = sourceIdentify.lastIndexOf("_");
        String idStr = sourceIdentify.substring(index + 1);
        if (Strings.isNullOrEmpty(idStr)) {
            return;
        }
        System.out.println(Long.valueOf(idStr));

        Boolean a = null;
        int b = a ? 1 : 0;
    }

    public void test() {
        String expression = "((y2-y1)/(x2-x1))*(x3-x2)+y2";
        Map<String, BigDecimal> data = Maps.newHashMap();
        data.put("y2", BigDecimal.valueOf(100));
        data.put("y1", BigDecimal.valueOf(0));
        data.put("x2", BigDecimal.valueOf(1));
        data.put("x1", BigDecimal.valueOf(0.5));
        data.put("x3", BigDecimal.valueOf(0.935048285));
        try {
            Object y3 = MVEL.eval(expression, data);
            BigDecimal result = new BigDecimal(y3.toString());
            System.out.println(result.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
