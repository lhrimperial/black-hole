package com.github.black.hole.sboot.server.persistence.entity.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/9/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeScoreConditionDO {
    /** 主键id */
    private Long id;
    /** 规则id */
    private Long ruleId;
    /** 评级得分 */
    private String score;
    /** 排名 */
    private Integer rank;
    /** 代理商id */
    private Long agencyId;
    /** 团队id */
    private Long teamId;
    /** 城市id */
    private Long cityId;
    /** 计算数据版本 */
    private Integer dataVersion;
    /** 站点ID列表 */
    private List<Long> teamIds;
    /** 规则ID */
    private List<Long> ruleIds;
    /** 页码 */
    private Integer offset;
    /** 分页大小 */
    private Integer limit;
}
