package com.github.black.hole.gateway.service;

import com.github.black.hole.gateway.entity.Datagram;
import com.github.black.hole.gateway.service.config.PathConfig;
import lombok.Data;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Data
public class GatewayContext {
    /** 配置 */
    private PathConfig config;
    /** 请求数据 */
    private Datagram<String> requestData;
}
