package com.github.black.hole.gateway.crypto.impl;

import com.github.black.hole.gateway.crypto.EncryptContext;
import com.github.black.hole.gateway.crypto.EncryptHandler;
import com.github.black.hole.gateway.enums.EncryptTypeEnum;
import com.github.black.hole.gateway.util.crypto.AESUtil;
import com.github.black.hole.gateway.util.crypto.aes.AesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Service
public class AesEncryptHandler implements EncryptHandler<EncryptContext> {

    private Logger logger = LoggerFactory.getLogger(AesEncryptHandler.class);

    @Override
    public String encrypt(EncryptContext context) {
        EncryptConfig config = context.getConfig();
        String encryptKey = config.getEncryptKey();
        String initVector = config.getInitVector();
        // 加密方式或填充方式为空
        if (StringUtils.isEmpty(config.getAesMode())
                || StringUtils.isEmpty(config.getAesFillWay())) {
            throw new IllegalArgumentException("encryptMethodConfig is incomplete!");
        }
        StringBuilder builder = new StringBuilder();
        builder.append(AesEnum.ALGORITHM_ENUM.AES.name())
                .append("/")
                .append(config.getAesMode())
                .append("/")
                .append(config.getAesFillWay());
        return AESUtil.encrypt(encryptKey, initVector, context.getDatagram(), builder.toString());
    }

    @Override
    public String decrypt(EncryptContext context) {
        EncryptConfig config = context.getConfig();
        String encryptKey = config.getEncryptKey();
        String initVector = config.getInitVector();
        // 加密方式或填充方式为空
        if (StringUtils.isEmpty(config.getAesMode())
                || StringUtils.isEmpty(config.getAesFillWay())) {
            throw new IllegalArgumentException("encryptMethodConfig is incomplete!");
        }
        StringBuilder builder = new StringBuilder();
        builder.append(AesEnum.ALGORITHM_ENUM.AES.name())
                .append("/")
                .append(config.getAesMode())
                .append("/")
                .append(config.getAesFillWay());

        return AESUtil.decrypt(encryptKey, initVector, context.getDatagram(), builder.toString());
    }

    @Override
    public EncryptTypeEnum cryptoWay() {
        return EncryptTypeEnum.AES;
    }
}
