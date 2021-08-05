package com.github.black.hole.vine;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2021/5/18
 */
public enum DomainAccountType {
    KEEPER(1, "饿了么商家账号"),
    COFFEE(2, "饿了么员工账号"),
    FAMILY(3, "饿了么销售BD代理商账号"),
    PASSPORT_AGENT(4, "passport代理商账号"),
    BAIDU_SHOP_ACCT(5, "百度商家账号"),
    PASSPORT_RIDER(6, "passport骑手账号"),
    PASSPORT_CROWD(7, "passport众包骑手账号"),
    PASSPORT_BAAS(8, "passport大前端账号"),
    PASSPORT_OPENAPI(9, "passport openapi账号"),
    PASSPORT_EUS(10, "passport主站账号"),
    EB_SHOP(11, "饿百商户"),
    DEFAULT(0, "default");

    private int value;
    private String description;

    private DomainAccountType(final int value, final String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static DomainAccountType fromValue(int value) {
        return Arrays.stream(DomainAccountType.values())
                .filter(item -> Objects.equals(value, item.getValue()))
                .findFirst()
                .orElse(null);
    }
}
