package com.github.black.hole.gateway.protocol.http;

import com.github.black.hole.gateway.constant.SysConstants;
import com.github.black.hole.gateway.entity.Datagram;
import com.github.black.hole.gateway.protocol.ProtocolHandler;
import com.github.black.hole.gateway.util.IPUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Service
public class HttpProtocolHandler implements ProtocolHandler<HttpProtocolContext> {

    private static Logger logger = LoggerFactory.getLogger(HttpProtocolHandler.class);

    @Override
    public Datagram<String> handleRequest(HttpProtocolContext context) {
        HttpServletRequest request = context.getRequest();
        Datagram<String> datagram = new Datagram<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            datagram.addHeader(headerName, request.getHeader(headerName));
        }

        String realIP = IPUtil.getRealIp(request);
        datagram.addHeader(SysConstants.REMOTE_HOST, realIP);

        try {
            String body = IOUtils.toString(request.getReader());
            datagram.setBody(body);
        } catch (IOException e) {
            logger.error("HttpProtocolHandler handleRequest fail", e);
            throw new IllegalStateException(e);
        }

        return datagram;
    }

    @Override
    public void handleResponse(HttpProtocolContext context) {
        Datagram<String> result = context.getResultData();
        if (result == null) {
            return;
        }

        HttpServletResponse response = context.getResponse();
        response.setCharacterEncoding("utf-8");
        try {
            Map<String, String> header = result.getHeader();
            for (Iterator<String> iterator = header.keySet().iterator(); iterator.hasNext(); ) {
                String headerName = iterator.next();
                response.addHeader(headerName, header.get(headerName));
            }
            response.getWriter().write(result.getBody());
        } catch (IOException e) {
            logger.error("HttpProtocol handleResponse fail", e);
            throw new IllegalStateException(e);
        }
    }
}
