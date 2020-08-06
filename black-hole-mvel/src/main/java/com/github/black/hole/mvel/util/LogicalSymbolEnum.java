package com.github.black.hole.mvel.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/7/22
 */
@Getter
@AllArgsConstructor
public enum LogicalSymbolEnum {
    /** 逻辑符号 */
    EQ("=="),
    GT(">"),
    LT("<"),
    GE(">="),
    LE("<="),
    AND("&&"),
    OR("||"),
    NOT("!"),
    ;
    private String symbol;

    public LogicalSymbolEnum typeOf(String symbol) {
        return Arrays.stream(LogicalSymbolEnum.values())
                .filter(item -> Objects.equals(symbol, item.getSymbol()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 包含边界
     *
     * @return
     */
    public static EnumSet<LogicalSymbolEnum> includeBoundary() {
        return EnumSet.of(EQ, GE, LE);
    }
}
