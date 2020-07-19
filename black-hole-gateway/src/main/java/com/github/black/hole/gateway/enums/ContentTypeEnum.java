package com.github.black.hole.gateway.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Getter
@AllArgsConstructor
public enum ContentTypeEnum {
    /** 请求数据格式 */
    JSON,
    XML,
    FORM,
    ;
}
