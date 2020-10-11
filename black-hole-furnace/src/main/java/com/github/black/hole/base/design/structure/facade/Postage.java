package com.github.black.hole.base.design.structure.facade;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class Postage {

    int getPostage(String addr) {
        // 模拟邮费计算
        return Math.abs(addr.hashCode()) % 20 + 6;
    }
}
