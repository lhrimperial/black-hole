package com.github.black.hole.gateway.service.config;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Data
@Builder
public class PathConfig {
    /** 请求地址 */
    private String url;
    /** 请求方式 */
    private String method;
    /** 注释 */
    private String comment;
    /** 内部接口 */
    private PathConfigFrom from;
    /** 参数 */
    private String params;
    /** 结果 */
    private String result;
    /** 插件 */
    private List<String> plugins;
    /** 标签 */
    private String tags;
    /** 版本 */
    private Integer version;
}
