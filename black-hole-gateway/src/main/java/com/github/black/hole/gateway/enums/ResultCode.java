package com.github.black.hole.gateway.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    /** result code */
    SUCCESS("10000", "成功"),
    ;

    private final String code;
    private final String message;
}
