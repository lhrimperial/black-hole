package com.github.black.hole.basic.redis;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/9/1
 */
public class RedisLock {

    private Jedis jedis;

    public RedisLock() {
        jedis = new Jedis("www.ifarmshop.com", 63799);
        jedis.auth("123456");
    }

    public boolean tryLock(String key, String requset, int timeout) {
        Long result = jedis.setnx(key, requset);
        // result = 1时，设置成功，否则设置失败
        if (result == 1L) {
            return jedis.expire(key, timeout) == 1L;
        } else {
            return false;
        }
    }

    public boolean tryLock_with_lua(String key, String UniqueId, int seconds) {
        String lua_scripts =
                "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then"
                        + "redis.call('expire',KEYS[1],ARGV[2]) return 1 else return 0 end";
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        keys.add(key);
        values.add(UniqueId);
        values.add(String.valueOf(seconds));
        Object result = jedis.eval(lua_scripts, keys, values);
        // 判断是否成功
        return result.equals(1L);
    }
}
