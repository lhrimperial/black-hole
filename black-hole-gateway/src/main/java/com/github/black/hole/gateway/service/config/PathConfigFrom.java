package com.github.black.hole.gateway.service.config;

import lombok.Data;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Data
public class PathConfigFrom {
    /** 应用appID */
    private String appId;
    /** 接口 */
    private String iface;
    /** 方法名称 */
    private String methodName;
}
