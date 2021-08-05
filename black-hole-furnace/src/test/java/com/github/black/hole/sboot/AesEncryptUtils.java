package com.github.black.hole.sboot;

import com.google.common.base.Strings;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加解密工具类
 *
 * @author hairen.long
 * @date 2019-05-07
 */
public class AesEncryptUtils {

    /** 16位密匙 */
    private static final String KEY = "4f8$19#2&9b1%*@5";
    /** 参数分别代表 算法名称/加密模式/数据填充方式 */
    private static final String ENCRYPT_ALGORITHM = "AES/ECB/PKCS5Padding";
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
     */
    public static String encrypt(String content, String encryptKey) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ENCRYPT_TYPE);
            keyGen.init(128);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(
                    Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), ENCRYPT_TYPE));
            byte[] bytes = cipher.doFinal(content.getBytes(DEFAULT_CHARSET));
            // 采用base64算法进行转码,避免出现中文乱码
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            throw new RuntimeException("加密失败");
        }
    }

    /**
     * 解密
     *
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return 解密结果
     */
    public static String decrypt(String encryptStr, String decryptKey) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ENCRYPT_TYPE);
            keyGen.init(KEY_GENERATOR_NUM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(
                    Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), ENCRYPT_TYPE));
            // 采用base64算法进行转码,避免出现中文乱码
            byte[] encryptBytes = Base64.decodeBase64(encryptStr);
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (Exception e) {
            throw new RuntimeException("解密失败");
        }
    }

    public static String encrypt(String content) {
        if (Strings.isNullOrEmpty(content)) {
            return "";
        }
        return encrypt(content, KEY);
    }

    public static String decrypt(String encryptStr) {
        if (Strings.isNullOrEmpty(encryptStr)) {
            return "";
        }
        return decrypt(encryptStr, KEY);
    }
}
