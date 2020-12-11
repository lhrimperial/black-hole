package com.github.black.hole.simple.mapstruct.polymerize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodInfo {
    private Long id;
    private String title;
    private double price;
    private int order;
    private Long typeId;
}
