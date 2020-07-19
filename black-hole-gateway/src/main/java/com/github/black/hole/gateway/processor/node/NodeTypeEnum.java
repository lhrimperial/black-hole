package com.github.black.hole.gateway.processor.node;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Getter
@AllArgsConstructor
public enum NodeTypeEnum {
    /** 网关节点类型 */
    WHITE_IP("白名单IP"),
    DECRYPT("解密"),
    PARSER("解析报文"),
    SIGN("签名"),
    RATE_LIMIT("限流"),
    DISPATCH("分发"),
    ;

    private final String desc;
}
