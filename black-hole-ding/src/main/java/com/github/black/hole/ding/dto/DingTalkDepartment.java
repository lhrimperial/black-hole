package com.github.black.hole.ding.dto;

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
public class DingTalkDepartment {
    /** 钉钉ID */
    private Long dingId;
    /** 部门名称 */
    private String name;
    /** 父部门ID */
    private Long parentId;
    /** 部门标识字段 */
    private String sourceIdentifier;
    /** 创建部门群 */
    private Boolean createDeptGroup;
    /** 如果有新人加入部门是否会自动加入部门群 */
    private Boolean autoAddUser;
    /** 部门的主管列表 */
    private String deptManagerUseridList;
    /** 企业群群主的userid */
    private String orgDeptOwner;
}
