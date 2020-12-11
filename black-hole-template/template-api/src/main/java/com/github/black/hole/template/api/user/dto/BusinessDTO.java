package com.github.black.hole.template.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hairen.long
 * @date 2020/11/27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDTO implements Serializable {
    private static final long serialVersionUID = -4737522985203323381L;
    /** 用户ID */
    private Long userId;
    /** 用户姓名 */
    private String userName;
}
