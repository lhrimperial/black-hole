package com.github.black.hole.basic;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author hairen.long
 * @date 2021/8/31
 */
public class BasicMain {

    public static void main(String[] args) {
        Cache<Long, Long> orgTeamMemoryDOCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(60 * 24, TimeUnit.MINUTES)
                        .maximumSize(15000L)
                        .build();
    }
}
