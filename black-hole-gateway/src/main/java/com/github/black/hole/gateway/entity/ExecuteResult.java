package com.github.black.hole.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ExecuteResult extends Result {
    /** 结果数据报 */
    private Datagram<String> datagram;
}
