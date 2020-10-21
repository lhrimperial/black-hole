package com.github.black.hole.sboot.ding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author hairen.long
 * @date 2020/10/19
 */
public class CallBackServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(CallBackServlet.class);

    private static final long serialVersionUID = 6133150606016495655L;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, Object> datagram = Maps.newHashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            datagram.put(headerName, request.getHeader(headerName));
        }
        logger.info("param:" + JSON.toJSONString(datagram));

        /** url中的签名 * */
        String msgSignature = request.getParameter("signature");
        /** url中的时间戳 * */
        String timeStamp = request.getParameter("timestamp");
        /** url中的随机字符串 * */
        String nonce = request.getParameter("nonce");
        /** 取得JSON对象中的encrypt字段 * */
        String encrypt = "";

        /** 获取post数据包数据中的加密数据 * */
        ServletInputStream sis = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(sis));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JSONObject body = JSON.parseObject(sb.toString());
        encrypt = body.getString("encrypt");
        logger.info("body:", body.toString());

        String token = "123456";
        String corpId = "dingb4fcf9bfab07559d35c2f4657eb6378f";
        byte[] aesKey = Base64.decodeBase64("c72XCVbpLUUqB3sRNR2KQwxtqnbqi2bkidGtN9Zi5jP" + "=");
        // 校验签名
        String signature = getSignature(token, timeStamp, nonce, encrypt);
        if (!signature.equals(msgSignature)) {
            logger.error("数组签名错误");
        }

        // 解密
        String decodeEncrypt = decrypt(encrypt, aesKey);
        JSONObject decodeEncryptJson = JSONObject.parseObject(decodeEncrypt);
        // 回调类型
        String eventType = decodeEncryptJson.getString("EventType");

        String resp = "success";
        String encryptRes = encrypt(getRandomStr(RANDOM_LENGTH), resp, corpId, aesKey);
        String signatureRes = getSignature(token, String.valueOf(timeStamp), nonce, encryptRes);

        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("msg_signature", signatureRes);
        resultMap.put("encrypt", encryptRes);
        resultMap.put("timeStamp", String.valueOf(timeStamp));
        resultMap.put("nonce", nonce);

        response.getWriter().write(JSON.toJSONString(resultMap));
    }

    public static void main(String[] args) throws Exception {
        byte[] aesKey = Base64.decodeBase64("c72XCVbpLUUqB3sRNR2KQwxtqnbqi2bkidGtN9Zi5jP" + "=");
        String str =
                "PYKLv6YVQcpQZhO3PVY198BCEB35CvosbOvRYfQQXbZzaoCVk1RWzCwuW9gLfWexbryoC5ftblmmsFBj1gMITw==";
        // 解密
        String decodeEncrypt = decrypt(str, aesKey);

        System.out.println(decodeEncrypt);
    }

    /*
     * 对明文加密.
     * @param text 需要加密的明文
     * @return 加密后base64编码的字符串
     */
    private String encrypt(String random, String plaintext, String corpId, byte[] aesKey) {
        try {
            byte[] randomBytes = random.getBytes(CHARSET);
            byte[] plainTextBytes = plaintext.getBytes(CHARSET);
            byte[] lengthByte = int2Bytes(plainTextBytes.length);
            byte[] corpidBytes = corpId.getBytes(CHARSET);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            byteStream.write(randomBytes);
            byteStream.write(lengthByte);
            byteStream.write(plainTextBytes);
            byteStream.write(corpidBytes);
            byte[] padBytes = getPaddingBytes(byteStream.size());
            byteStream.write(padBytes);
            byte[] unencrypted = byteStream.toByteArray();
            byteStream.close();
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encrypted = cipher.doFinal(unencrypted);
            String result = new Base64().encodeToString(encrypted);
            return result;
        } catch (Exception e) {
            logger.error("加密异常");
        }
        return "";
    }

    public static byte[] getPaddingBytes(int count) {
        int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
        if (amountToPad == 0) {
            amountToPad = BLOCK_SIZE;
        }
        char padChr = chr(amountToPad);
        String tmp = new String();
        for (int index = 0; index < amountToPad; index++) {
            tmp += padChr;
        }
        return tmp.getBytes(CHARSET);
    }

    private static char chr(int a) {
        byte target = (byte) (a & 0xFF);
        return (char) target;
    }

    /** @return */
    public static String getRandomStr(int count) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private static final Integer RANDOM_LENGTH = 16;
    private static final int BLOCK_SIZE = 32;
    private static final Charset CHARSET = Charset.forName("utf-8");

    private static String decrypt(String text, byte[] aesKey) {
        String plainText = "";
        String fromCorpid = "";
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decodeBase64(text);
            System.out.println("encrypted: " + new String(encrypted));
            // 解密
            byte[] originalArr = cipher.doFinal(encrypted);
            System.out.println("originalArr: " + new String(originalArr));
            // 去除补位字符
            int pad = (int) originalArr[originalArr.length - 1];
            if (pad < 1 || pad > BLOCK_SIZE) {
                pad = 0;
            }
            byte[] bytes = Arrays.copyOfRange(originalArr, 0, originalArr.length - pad);
            System.out.println("bytes: " + new String(bytes));
            // 分离16位随机字符串,网络字节序和corpId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
            System.out.println("networkOrder: " + new String(networkOrder));
            int plainTextLegth = bytes2int(networkOrder);
            plainText = new String(Arrays.copyOfRange(bytes, 20, 20 + plainTextLegth), CHARSET);
            fromCorpid =
                    new String(
                            Arrays.copyOfRange(bytes, 20 + plainTextLegth, bytes.length), CHARSET);
        } catch (Exception e) {
            logger.error("解密异常", e);
        }
        return plainText;
    }

    /*
     * int转byte数组,高位在前
     */
    public static byte[] int2Bytes(int count) {
        byte[] byteArr = new byte[4];
        byteArr[3] = (byte) (count & 0xFF);
        byteArr[2] = (byte) (count >> 8 & 0xFF);
        byteArr[1] = (byte) (count >> 16 & 0xFF);
        byteArr[0] = (byte) (count >> 24 & 0xFF);
        return byteArr;
    }

    /**
     * 高位在前bytes数组转int
     *
     * @param byteArr
     * @return
     */
    public static int bytes2int(byte[] byteArr) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            count <<= 8;
            count |= byteArr[i] & 0xff;
        }
        return count;
    }

    /**
     * 数字签名
     *
     * @param token isv token
     * @param timestamp 时间戳
     * @param nonce 随机串
     * @param encrypt 加密文本
     * @return
     * @throws Exception
     */
    public String getSignature(String token, String timestamp, String nonce, String encrypt) {
        try {
            String[] array = new String[] {token, timestamp, nonce, encrypt};
            Arrays.sort(array);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            logger.error("数字签名", e);
        }
        return "";
    }
}
