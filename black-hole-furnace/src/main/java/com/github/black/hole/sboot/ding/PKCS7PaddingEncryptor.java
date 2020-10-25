package com.github.black.hole.sboot.ding;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author hairen.long
 * @date 2020/10/19
 */
public class PKCS7PaddingEncryptor {

    private String ALGO = "AES";
    private String ALGO_MODE = "AES/CBC/NoPadding";
    private String akey = "11111111111111111111111111111111";
    private String aiv = "22222222222222222222222222222222";

    public static void main(String[] args) throws Exception {
        PKCS7PaddingEncryptor aes = new PKCS7PaddingEncryptor();
        // 创建Json的加密对象
        JSONObject data = new JSONObject();
        data.put("haha", "hehe");
        System.out.println("原始数据:" + data.toJSONString());
        // 进行PKCS7Padding填充
        String rstData = pkcs7padding(data.toJSONString());
        // 进行java的AES/CBC/NoPadding加密
        byte[] aesKey = Base64.decodeBase64("c72XCVbpLUUqB3sRNR2KQwxtqnbqi2bkidGtN9Zi5jP" + "=");
        String passwordEnc = aes.encrypt(aesKey, rstData);
        // 解密
        String passwordDec = aes.decrypt(aesKey, passwordEnc);
        System.out.println("加密之后的字符串:" + passwordEnc);
        System.out.println("解密后的数据:" + passwordDec);

        System.out.println(
                aes.decrypt(
                        aesKey,
                        "PYKLv6YVQcpQZhO3PVY198BCEB35CvosbOvRYfQQXbZzaoCVk1RWzCwuW9gLfWexbryoC5ftblmmsFBj1gMITw=="));
    }

    public String encrypt(byte[] aesKeys, String data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(ALGO_MODE);
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(aesKeys, ALGO);
            IvParameterSpec ivspec = new IvParameterSpec(aesKeys, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            // 将cipher加密后的byte数组用base64加密生成字符串
            String EncStr = Base64.encodeBase64String(encrypted);
            return EncStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(byte[] aesKeys, String encryptedData) throws Exception {
        try {
            byte[] encrypted = Base64.decodeBase64(encryptedData);
            Cipher cipher = Cipher.getInstance(ALGO_MODE);
            SecretKeySpec keyspec = new SecretKeySpec(aesKeys, ALGO);
            IvParameterSpec ivspec = new IvParameterSpec(aesKeys, 0, 16);
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted);
            String originalString = new String(original);
            // 此处添加trim（）是为了去除多余的填充字符，就不用去填充了，具体有什么问题我还没有遇到，有强迫症的同学可以自己写一个PKCS7UnPadding函数
            return originalString.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 此函数是将字符串每两个字符合并生成byte数组
     *
     * @param hexString
     * @return
     */
    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() >> 1];
        int index = 0;
        for (int i = 0; i < hexString.length(); i++) {
            if (index > hexString.length() - 1) {
                return byteArray;
            }
            byte highDit = (byte) (Character.digit(hexString.charAt(index), 16) & 0xFF);
            byte lowDit = (byte) (Character.digit(hexString.charAt(index + 1), 16) & 0xFF);
            byteArray[i] = (byte) (highDit << 4 | lowDit);
            index += 2;
        }
        return byteArray;
    }

    /**
     * 此函数是pkcs7padding填充函数
     *
     * @param data
     * @return
     */
    public static String pkcs7padding(String data) {
        int bs = 16;
        int padding = bs - (data.length() % bs);
        String padding_text = "";
        for (int i = 0; i < padding; i++) {
            padding_text += (char) padding;
        }
        return data + padding_text;
    }
}
