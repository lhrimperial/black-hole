package com.github.black.hole.sboot.util;

import com.github.black.hole.sboot.common.ServiceException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * @author hairen.long
 * @date 2020/9/27
 */
public class AesEncryptUtils {

    private static final Logger logger = LoggerFactory.getLogger(AesEncryptUtils.class);

    /** 参数分别代表 算法名称/加密模式/数据填充方式 */
    private static final String ENCRYPT_ALGORITHM = "AES/CBC/PKCS7Padding";

    /** 参数分别代表 算法名称/加密模式/数据填充方式 */
    private static final String ENCRYPT_TYPE = "AES";

    /** 默认字符编码 */
    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final Integer KEY_GENERATOR_NUM = 128;

    /**
     * 加密
     *
     * @param content 加密的字符串
     * @param encryptKey key值
     * @return 加密结果
     * @throws ServiceException 业务异常
     */
    public static String encrypt(String content, String encryptKey) throws ServiceException {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            KeyGenerator kgen = KeyGenerator.getInstance(ENCRYPT_TYPE);
            kgen.init(KEY_GENERATOR_NUM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            byte[] aesKey = Base64.decodeBase64(encryptKey + "=");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, ENCRYPT_TYPE);
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] bytes = cipher.doFinal(content.getBytes(DEFAULT_CHARSET));
            // 采用base64算法进行转码,避免出现中文乱码
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            logger.error("加密失败", e);
            throw new ServiceException("加密失败");
        }
    }

    /**
     * 解密
     *
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return 解密结果
     * @throws ServiceException 业务异常
     */
    public static String decrypt(String encryptStr, String decryptKey) throws ServiceException {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            KeyGenerator kgen = KeyGenerator.getInstance(ENCRYPT_TYPE);
            kgen.init(KEY_GENERATOR_NUM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            byte[] aesKey = Base64.decodeBase64(decryptKey + "=");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, ENCRYPT_TYPE);
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            // 采用base64算法进行转码,避免出现中文乱码
            byte[] encryptBytes = Base64.decodeBase64(encryptStr);
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (Exception e) {
            logger.error("解密失败", e);
            throw new ServiceException("解密失败");
        }
    }
}
