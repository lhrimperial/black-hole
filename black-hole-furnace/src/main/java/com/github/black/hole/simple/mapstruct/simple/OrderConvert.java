package com.github.black.hole.simple.mapstruct.simple;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
@Mapper()
public interface OrderConvert {

    /**
     * convert
     *
     * @param order
     * @return
     */
    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "createTime", target = "orderTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    OrderDTO from(OrderDO order);
}
