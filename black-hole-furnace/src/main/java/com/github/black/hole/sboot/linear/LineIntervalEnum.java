package com.github.black.hole.sboot.linear;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author hairen.long
 * @date 2020/7/20
 */
@Getter
@AllArgsConstructor
public enum LineIntervalEnum {
    /** 开闭区间属性 */
    OPEN(0, "开区间"),
    CLOSE(1, "闭区间");

    private final int code;
    private final String desc;

    public static LineIntervalEnum typeOf(int code) {
        return Stream.of(LineIntervalEnum.values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElse(null);
    }

    public static Boolean notValidType(Integer code) {
        return Objects.isNull(typeOf(code));
    }
}
