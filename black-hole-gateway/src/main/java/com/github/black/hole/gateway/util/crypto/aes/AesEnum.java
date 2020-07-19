package com.github.black.hole.gateway.util.crypto.aes;
/**
 * @author hairen.long
 * @date 2020/7/19
 */
public class AesEnum {

    public static enum ALGORITHM_ENUM {
        AES;
    }

    public static enum MODE_ENUM {
        CBC,
        ECB,
        CTR,
        OCF,
        CFB;
    }

    public static enum FILL_WAY_ENUM {
        NoPadding,
        PKCS5Padding,
        PKCS7Padding,
        ZerosPadding,
        ISO10126Padding;
    }
}
