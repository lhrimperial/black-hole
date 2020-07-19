package com.github.black.hole.gateway.controller;

import com.github.black.hole.gateway.entity.Datagram;
import com.github.black.hole.gateway.entity.ExecuteResult;
import com.github.black.hole.gateway.protocol.ProtocolHandler;
import com.github.black.hole.gateway.protocol.http.HttpProtocolContext;
import com.github.black.hole.gateway.service.GatewayContext;
import com.github.black.hole.gateway.service.GatewayService;
import com.github.black.hole.gateway.service.config.PathConfig;
import com.github.black.hole.gateway.service.config.PathConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@RestController
@RequestMapping
public class GatewayController {

    @Autowired ProtocolHandler protocolHandler;
    @Autowired GatewayService gatewayService;
    @Autowired PathConfigService pathConfigService;

    @GetMapping("/**")
    public void get(HttpServletRequest request, HttpServletResponse response) {
        gateway(request.getServletPath(), request, response);
    }

    @PostMapping("/**")
    public void post(HttpServletRequest request, HttpServletResponse response) {
        gateway(request.getServletPath(), request, response);
    }

    private void gateway(String path, HttpServletRequest request, HttpServletResponse response) {
        HttpProtocolContext protocolContext = new HttpProtocolContext();
        protocolContext.setRequest(request);
        // 解析请求参数
        Datagram<String> requestData = protocolHandler.handleRequest(protocolContext);
        // 加载配置
        PathConfig config = pathConfigService.getConfig(path);

        GatewayContext context = new GatewayContext();
        context.setRequestData(requestData);
        context.setConfig(config);
        ExecuteResult result = gatewayService.execute(context);

        protocolContext.setResponse(response);
        protocolContext.setResultData(result.getDatagram());

        protocolHandler.handleResponse(protocolContext);
    }
}
