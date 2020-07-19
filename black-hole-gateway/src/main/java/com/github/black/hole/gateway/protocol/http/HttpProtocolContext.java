package com.github.black.hole.gateway.protocol.http;

import com.github.black.hole.gateway.entity.Datagram;
import com.github.black.hole.gateway.enums.ContentTypeEnum;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Data
public class HttpProtocolContext {
    /** 请求数据格式 */
    private ContentTypeEnum contentType;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private Datagram<String> resultData;
}
