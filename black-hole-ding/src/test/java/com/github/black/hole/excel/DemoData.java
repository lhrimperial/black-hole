package com.github.black.hole.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hairen.long
 * @date 2020/5/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoData {
    /** id */
    private Long areaId;
    /** 名称 */
    private String name;
    /** 父ID */
    private Long pid;
    /** 等级 */
    private Integer level;
}
