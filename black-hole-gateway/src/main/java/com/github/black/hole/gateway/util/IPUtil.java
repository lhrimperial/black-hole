package com.github.black.hole.gateway.util;

import com.github.black.hole.gateway.constant.SysConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
public class IPUtil {

    private static final String UNKNOWN_IP = "unknown";

    /**
     * 获取真实IP
     *
     * @param request 请求体
     * @return 真实IP
     */
    public static String getRealIp(HttpServletRequest request) {
        // 这个一般是Nginx反向代理设置的参数
        String ip = request.getHeader("X-Real-IP");
        if (Objects.isNull(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (Objects.isNull(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (Objects.isNull(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (Objects.isNull(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多IP的情况（只取第一个IP）
        if (Objects.nonNull(ip) && ip.contains(SysConstants.COMMA)) {
            String[] ipArray = ip.split(",");
            ip = ipArray[0];
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
