package com.github.black.hole.sboot;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/3/22
 */
public class TokenUtils {
    private static final String JWT_SECRET = "4f8$19#2&9b1%*@";
    public static final int JWT_TTL = 60 * 60 * 1000;

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public SecretKey generalKey() {
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(JWT_SECRET);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 创建jwt
     *
     * @param id
     * @param issuer
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public String createJWT(String id, String issuer, String subject, long ttlMillis)
            throws Exception {

        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", "123456");
        claims.put("user_name", "admin");
        claims.put("nick_name", "X-rapido");

        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder =
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setIssuer(issuer)
                        .setSubject(subject)
                        .signWith(signatureAlgorithm, key);

        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception {
        // 签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey();
        // 得到DefaultJwtParser //设置签名的秘钥 //设置需要解析的jwt
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
    }

    public static void main(String[] args) {
        String mobile = "13640599896";

        String result = AesEncryptUtils.encrypt(mobile);

        System.out.println(AesEncryptUtils.decrypt("P4smzM6CXTZtdBxVt6xlrQ=="));
        System.out.println(Base64.encodeBase64String("HUMMINGBIRD_COLLEGE".getBytes()));
        String subject = "hello";

        try {
            TokenUtils util = new TokenUtils();
            String jwt = util.createJWT("JWT_ID", "Anson", subject, JWT_TTL);
            System.out.println("JWT：" + jwt);

            System.out.println("\n解密\n");

            Claims c = util.parseJWT(jwt);
            System.out.println(c.getId());
            System.out.println(c.getIssuedAt());
            System.out.println(c.getSubject());
            System.out.println(c.getIssuer());
            System.out.println(c.get("uid", String.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
