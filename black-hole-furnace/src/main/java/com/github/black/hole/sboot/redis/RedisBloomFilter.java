package com.github.black.hole.sboot.redis;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * @author hairen.long
 * @date 2021/9/13
 */
//@Component
public class RedisBloomFilter<T> {
    private JedisCluster cluster;

    public RedisBloomFilter(JedisCluster jedisCluster) {
        this.cluster = jedisCluster;
    }

    /**
     * 根据给定的布隆过滤器添加值
     */
    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            //redisTemplate.opsForValue().setBit(key, i, true);
            cluster.setbit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     */
    public <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            //if (!redisTemplate.opsForValue().getBit(key, i)) {
            if (!cluster.getbit(key, i)) {
                return false;
            }
        }

        return true;
    }
}