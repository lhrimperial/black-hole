package com.github.black.hole.gateway.crypto;

import com.github.black.hole.gateway.crypto.impl.EncryptConfig;
import lombok.Data;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Data
public class EncryptContext {
    /** 加解密配置 */
    private EncryptConfig config;
    /** 数据报 */
    private String datagram;
}
