package com.github.black.hole.gateway.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Data
public class Datagram<T> {

    private Map<String, T> header = new HashMap<>();

    private T body;

    public void addHeader(String name, T value) {
        this.header.put(name, value);
    }
}
