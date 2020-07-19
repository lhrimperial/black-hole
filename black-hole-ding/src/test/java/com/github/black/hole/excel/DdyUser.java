package com.github.black.hole.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hairen.long
 * @date 2020/5/26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DdyUser {
    private String name;
    private String email;
    private String aliEmail;
    private String areaName;
    private Long areaId;
    private Integer level;
    private Integer roleId;
}
