package com.github.black.hole;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.formula.functions.T;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/1/20
 */
public class LongTest {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        List<Integer> list = IntStream.range(1, 200).boxed().collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));
        //正确
        list.removeIf(i -> i % 10 == 0);

        System.out.println(JSON.toJSONString(list));

        list = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));
    }
}
