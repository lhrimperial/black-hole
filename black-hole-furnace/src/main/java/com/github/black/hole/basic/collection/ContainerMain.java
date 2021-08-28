package com.github.black.hole.basic.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hairen.long
 * @date 2021/8/14
 */
public class ContainerMain {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.indexOf("");

        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);

        Map<Integer, Integer> currMap = new ConcurrentHashMap<>();
        currMap.put(1, 1);
    }
}
