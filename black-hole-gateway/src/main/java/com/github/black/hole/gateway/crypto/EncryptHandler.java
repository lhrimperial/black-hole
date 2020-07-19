package com.github.black.hole.gateway.crypto;

import com.github.black.hole.gateway.enums.EncryptTypeEnum;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
public interface EncryptHandler<T> {
    /**
     * 加密
     *
     * @param context
     * @return
     */
    String encrypt(T context);

    /**
     * 解密
     *
     * @param context
     * @return
     */
    String decrypt(T context);

    /**
     * 加密方式
     *
     * @return
     */
    EncryptTypeEnum cryptoWay();
}
