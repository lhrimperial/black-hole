package com.github.black.hole.basic.redis;

import org.junit.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author hairen.long
 * @date 2021/9/1
 */
public class RedisClient {

    private static final String host = "www.ifarmshop.com";
    private static final int port = 63799;
    private static final String password = "123456";
    private static Jedis instance;
    private static JedisPoolConfig poolConfig;
    private static JedisPool jedisPool;

    public static Jedis getClient() {
        if (instance == null) {
            synchronized (RedisClient.class) {
                if (instance == null) {
                    instance = new Jedis(host, port);
                    instance.auth(password);
                }
            }
        }
        return instance;
    }

    private JedisPoolConfig getPoolConfig() {
        if (poolConfig == null) {
            poolConfig = new JedisPoolConfig();
            // 最大连接数
            poolConfig.setMaxTotal(1024);
            // 最大空闲连接数
            poolConfig.setMaxIdle(200);
            // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
            poolConfig.setMaxWaitMillis(1000);
            // 在获取连接的时候检查有效性, 默认false
            poolConfig.setTestOnBorrow(true);
            // 在获取返回结果的时候检查有效性, 默认false
            poolConfig.setTestOnReturn(true);
        }
        return poolConfig;
    }

    /** 初始化JedisPool */
    private void initJedisPool() {
        if (jedisPool == null) {
            // 初始化连接池
            jedisPool = new JedisPool(getPoolConfig(), host, port);
        }
    }

    public static void main(String[] args) {
        Jedis jedis = getClient();
        long r = jedis.hset("user", "name", "yangxin");
        jedis.hset("user", "age", "18");
        // 18显得我年轻~
        Assert.assertEquals(r, 1);
        // 如果key不存在则添加，否则操作失败。key存在返回0,否则返回1
        r = jedis.hsetnx("user", "name", "郭德纲");
        Assert.assertTrue(r == 0);
        // 获取某一key的值
        String s = jedis.hget("user", "name");
        System.out.println(s);
    }
}
