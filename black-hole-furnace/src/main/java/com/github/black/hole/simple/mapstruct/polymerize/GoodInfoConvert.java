package com.github.black.hole.simple.mapstruct.polymerize;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
@Mapper(componentModel = "spring")
public interface GoodInfoConvert {

    /** Long => String 隐式类型转换 */
    @Mapping(source = "good.id", target = "goodId")
    /** 属性名不同， */
    @Mapping(source = "type.name", target = "typeName")
    /** 属性名不同 */
    @Mapping(source = "good.title", target = "goodName")
    /** 属性名不同 */
    @Mapping(source = "good.price", target = "goodPrice")
    GoodInfoDTO from(GoodInfo good, GoodType type);
}
