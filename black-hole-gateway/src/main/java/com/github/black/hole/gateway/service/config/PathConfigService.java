package com.github.black.hole.gateway.service.config;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
public interface PathConfigService {

    /**
     * 获取清楚配置
     *
     * @param path
     * @return
     */
    PathConfig getConfig(String path);
}
