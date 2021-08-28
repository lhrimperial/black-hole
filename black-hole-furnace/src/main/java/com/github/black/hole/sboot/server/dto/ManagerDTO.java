package com.github.black.hole.sboot.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author hairen.long
 * @date 2021/8/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO {
    /** 绑定用户ID */
    private Long bindUserId;
    /** 绑定用户名称 */
    private String bindUserName;
    /** 资源ID */
    private Long resourceId;
    /** 资源名称 */
    private String resourceName;
}
