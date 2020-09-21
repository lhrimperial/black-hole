package com.github.black.hole.sboot.server.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author hairen.long
 * @date 2020/9/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeScoreDO {
    /** 主键id */
    private Long id;
    /** 规则id */
    private Long ruleId;
    /** 评级得分 */
    private String score;
    /** 排名 */
    private Integer rank;
    /** 城市id */
    private Long cityId;
    /** 计算数据版本 */
    private Integer dataVersion;
    /** 软删,1:有效,0:删除 */
    private Integer isActive;
    /** 创建时间 */
    private Timestamp createdAt;
    /** 更新时间 */
    private Timestamp updatedAt;
}
