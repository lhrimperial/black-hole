package com.github.black.hole.simple.mapstruct.simple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDO {
    /** 订单 Id */
    private Long id;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 订单金额 */
    private Long amount;

    /** 支付状态 */
    private Integer payStatus;

    /** 创建时间 */
    private LocalDateTime createTime;
}
