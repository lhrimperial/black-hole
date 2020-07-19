package com.github.black.hole.ding.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hairen.long
 * @date 2020/5/8
 */
@Getter
@AllArgsConstructor
public enum DingErrorCodeEnum {
    /** 系统返回码 */
    SYS_BUSY(-1L, "系统繁忙"),
    SUCCESS(0L, "系统繁忙"),
    AUTH_ERROR(88L, "鉴权异常"),
    ;
    private Long errCode;
    private String errMsg;
}
