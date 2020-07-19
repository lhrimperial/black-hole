package com.github.black.hole.gateway.util.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * * @author hairen.long * @date 2020/7/19 AES
 *
 * <p>加解密工具类 java 自带不支持PKCS7Padding填充
 *
 * <p>java本身限制密钥的长度最多128位，其他长度需要额外库支持
 */
public class AESUtil {
    private static Logger logger = LoggerFactory.getLogger(AESUtil.class);
    /** AES密码器 */
    private static Cipher cipher;
    /** 字符串编码 */
    private static final String KEY_CHARSET = "UTF-8";
    /** 算法方式 */
    private static final String KEY_ALGORITHM = "AES";
    /** 算法/模式/填充 */
    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    /** 私钥大小128/192/256(bits)位 即：16/24/32bytes，暂时使用128 */
    private static final Integer PRIVATE_KEY_SIZE_BIT = 128;

    private static final Integer[] KEY_SIZE_ARR = {16, 24, 32};

    /**
     * 加密
     *
     * @param secretKey
     * @param data
     * @return
     */
    public static String encrypt(
            String secretKey, String initVector, String data, String instanceParam) {
        Integer keyLength = secretKey.length();
        if (!Arrays.asList(KEY_SIZE_ARR).contains(keyLength)) {
            throw new RuntimeException(
                    "AESUtil:Invalid AES secretKey length (must be 16/24/32 bytes)");
        }
        // 密文字符串
        String cipherString = "";
        try {
            // 加密模式初始化参数
            initParam(
                    secretKey, initVector, Cipher.ENCRYPT_MODE, secretKey.length(), instanceParam);
            // 获取加密内容的字节数组
            byte[] dataBytes = data.getBytes(KEY_CHARSET);
            // 执行加密
            byte[] cipherBytes = cipher.doFinal(dataBytes);
            cipherString = encodeBytes(cipherBytes);
        } catch (Exception e) {
            logger.error("AESUtil:encrypt fail!", e);
            throw new RuntimeException("AESUtil:encrypt fail!", e);
        }
        return cipherString;
    }

    /**
     * 解密
     *
     * @param secretKey
     * @param cipherData
     * @param instanceParam
     * @return
     */
    public static String decrypt(
            String secretKey, String initVector, String cipherData, String instanceParam) {
        Integer keyLength = secretKey.length();
        if (!Arrays.asList(KEY_SIZE_ARR).contains(keyLength)) {
            throw new RuntimeException(
                    "AESUtil:Invalid AES secretKey length (must be 16/24/32 bytes)");
        }
        // 明文字符串
        String data = "";
        try {
            initParam(
                    secretKey, initVector, Cipher.DECRYPT_MODE, secretKey.length(), instanceParam);
            // 将加密并编码后的内容解码成字节数组
            byte[] cipherBytes = decodeBytes(cipherData);
            // 解密
            byte[] dataBytes = cipher.doFinal(cipherBytes);
            data = new String(dataBytes, KEY_CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("AESUtil:decrypt fail!", e);
        }
        return data;
    }

    public static void initParam(
            String secretKey,
            String initVector,
            int mode,
            Integer keyLength,
            String instanceParam) {
        try {
            // 防止Linux下生成随机key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(secretKey.getBytes());
            // 获取key生成器
            if (keyLength == null) {
                keyLength = PRIVATE_KEY_SIZE_BIT;
            } else {
                keyLength *= 8;
            }
            KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
            keygen.init(keyLength, secureRandom);

            // 获得原始对称密钥的字节数组
            byte[] raw = secretKey.getBytes();

            // 根据字节数组生成AES内部密钥
            SecretKeySpec key = new SecretKeySpec(raw, KEY_ALGORITHM);
            // 根据指定算法"AES/CBC/PKCS5Padding"实例化密码器
            if (StringUtils.isEmpty(instanceParam)) {
                instanceParam = CIPHER_ALGORITHM_CBC;
            }
            cipher = Cipher.getInstance(instanceParam);
            if (StringUtils.isEmpty(initVector)) {
                initVector = secretKey;
            }
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());
            cipher.init(mode, key, iv);
        } catch (Exception e) {
            throw new RuntimeException("AESUtil:initParam fail!", e);
        }
    }

    /**
     * 转16进制
     *
     * @param bytes
     * @return
     */
    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }
        return strBuf.toString();
    }
    /**
     * 转字节数组
     *
     * @param str
     * @return
     */
    public static byte[] decodeBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            char c = str.charAt(i);
            bytes[i / 2] = (byte) ((c - 'a') << 4);
            c = str.charAt(i + 1);
            bytes[i / 2] += (c - 'a');
        }
        return bytes;
    }
}
