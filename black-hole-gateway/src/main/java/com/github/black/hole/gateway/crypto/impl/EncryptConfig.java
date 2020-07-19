package com.github.black.hole.gateway.crypto.impl;

import com.github.black.hole.gateway.enums.EncryptTypeEnum;
import lombok.Data;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Data
public class EncryptConfig {

    private EncryptTypeEnum encryptType;

    private String encryptKey;

    private String initVector;

    private String aesMode;

    private String aesFillWay;
}
