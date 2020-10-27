package com.github.black.hole.base.limiter.lock;

/**
 * @author hairen.long
 * @date 2020/10/27
 */
public interface Lock {

    /**
     * 锁
     *
     * @param uniqueKey 加锁唯一键标识
     * @return 加锁/获取锁成功则返回true，否则返回false
     */
    boolean lock(String uniqueKey);

    /**
     * 有过期时间锁
     *
     * @param uniqueKey 加锁唯一键标识
     * @param expireTime 过期时间，单位秒
     * @return 加锁/获取锁成功则返回true，否则返回false
     */
    boolean lock(String uniqueKey, int expireTime);

    /**
     * 有过期时间和指定许可的锁
     *
     * @param uniqueKey 加锁唯一键标识
     * @param expireTime 过期时间，单位秒
     * @param permits 许可数量(最大并发量，默认为1)
     * @return 加锁/获取锁成功则返回true，否则返回false
     */
    boolean lock(String uniqueKey, int expireTime, int permits);

    /**
     * 释放锁
     *
     * @param uniqueKey 加锁唯一键标识
     * @return
     */
    boolean releaseLock(String uniqueKey);
}
